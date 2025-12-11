/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.DocumentTypeDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author esteb
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DocumentTypeDao documentTypeDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 5;

    @Override
    public void increaseFailedAttempts(User user) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        userDao.save(user);
    }

    @Override
    public void resetFailedAttempts(User user) {
        user.setFailedAttempts(0);
        user.setLockTime(null);
        userDao.save(user);
    }

    @Override
    public void lock(User user) {
        user.setAccountLocked(true);
        user.setLockTime(LocalDateTime.now());
        userDao.save(user);
    }

    @Override
    public boolean unlockIfTimeExpired(User user) {
        if (user.getLockTime() == null) {
            return true;
        }

        LocalDateTime unlockTime = user.getLockTime().plusMinutes(LOCK_DURATION_MINUTES);

        if (LocalDateTime.now().isAfter(unlockTime)) {
            user.setAccountLocked(false);
            resetFailedAttempts(user);
            return true;
        }

        return false;
    }

    @Override
    public String updateUserProfile(String email, String fullName,
                                    String currentPassword,
                                    String newPassword,
                                    String confirmNewPassword) {

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setFullName(fullName);

        boolean wantsToChangePassword =
                (currentPassword != null && !currentPassword.isBlank()) ||
                        (newPassword != null && !newPassword.isBlank()) ||
                        (confirmNewPassword != null && !confirmNewPassword.isBlank());

        // Si el usuario quiere cambiar la contraseña tiene que estar completo todos los campos
        if (wantsToChangePassword) {

            // Campos completos
            if (currentPassword == null || currentPassword.isBlank() ||
                    newPassword == null || newPassword.isBlank() ||
                    confirmNewPassword == null || confirmNewPassword.isBlank()) {
                return "error: Debe completar todos los campos para cambiar la contraseña.";
            }

            // Validar contraseña actual
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                return "error: La contraseña actual no es correcta.";
            }

            // Nueva vs confirmación
            if (!newPassword.equals(confirmNewPassword)) {
                return "error: La nueva contraseña y su confirmación no coinciden.";
            }

            // Evita misma contraseña
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                return "error: La nueva contraseña no puede ser igual a la actual.";
            }

            // Validaciones de seguridad
            if (newPassword.length() < 12 || newPassword.length() > 18) {
                return "error: La contraseña debe tener entre 12 y 18 caracteres.";
            }

            if (!newPassword.matches(".*[A-Z].*")) {
                return "error: La contraseña debe incluir al menos una mayúscula.";
            }

            if (!newPassword.matches(".*[0-9].*")) {
                return "error: La contraseña debe incluir al menos un número.";
            }

            // Guarda la contraseña nueva
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        // Guarda cambios si el usuario no modifica la contraseña
        save(user);

        return "success: Datos actualizados correctamente.";
    }


    @Override
    public Optional<User> findByIdentification(String identification) {
        // Mantener por compatibilidad; idealmente migrar a findByDocument(...)
        return userDao.findByIdentification(identification);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.findByEmail(email).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Override
    public void save(User user) {
        validateDocument(user.getDocumentType(), user.getIdentification(), user);
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        validateDocument(user.getDocumentType(), user.getIdentification(), user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userDao.deleteByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        // Mantener por compatibilidad; idealmente migrar a existsByDocument(...)
        return userDao.existsByIdentification(identification);
    }

    @Override
    public void disableByEmail(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(false);
            userDao.save(user);
        }
    }

    @Override
    public Optional<User> findByDocument(DocumentType documentType, String identification) {
        return userDao.findByDocumentTypeAndIdentification(documentType, identification);
    }

    @Override
    public Optional<User> findByDocumentCode(String documentTypeCode, String identification) {
        return userDao.findByDocumentTypeCodeAndIdentification(documentTypeCode, identification);
    }

    @Override
    public boolean existsByDocument(DocumentType documentType, String identification) {
        return userDao.existsByDocumentTypeAndIdentification(documentType, identification);
    }

    @Override
    public boolean existsByDocumentCode(String documentTypeCode, String identification) {
        return userDao.existsByDocumentTypeCodeAndIdentification(documentTypeCode, identification);
    }

    private void validateDocument(DocumentType type, String number, User currentUser) {
        if (type == null && (number == null || number.isBlank())) {
            return; // permitido si tu negocio lo admite (empleados sin doc, etc.)
        }
        if (type == null) {
            throw new IllegalArgumentException("Debe seleccionar el tipo de documento1.");
        }
        if (Boolean.FALSE.equals(type.getIsActive())) {
            throw new IllegalArgumentException("El tipo de documento seleccionado no está activo.");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar el número de identificación.");
        }
        if (type.getMinLength() != null && number.length() < type.getMinLength()) {
            throw new IllegalArgumentException("La identificación es demasiado corta para " + type.getName() + ".");
        }
        if (type.getMaxLength() != null && number.length() > type.getMaxLength()) {
            throw new IllegalArgumentException("La identificación es demasiado larga para " + type.getName() + ".");
        }
        if (type.getPatternRegex() != null && !type.getPatternRegex().isBlank() && !number.matches(type.getPatternRegex())) {
            throw new IllegalArgumentException("El formato de la identificación no es válido para " + type.getName() + ".");
        }

        Optional<User> existing = userDao.findByDocumentTypeAndIdentification(type, number);
        if (existing.isPresent()) {
            Long existingId = existing.get().getIdUser();
            Long currentId = (currentUser != null) ? currentUser.getIdUser() : null;

            // Si estoy creando (currentId == null) o es distinto usuario, es conflicto
            if (!existingId.equals(currentId)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese tipo y número de identificación.");
            }
        }
    }

}
