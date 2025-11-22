package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.dao.DocumentTypeDao;
import com.motollantas.MotoLlantasVirtual.dao.registerDao;
import com.motollantas.MotoLlantasVirtual.domain.DocumentType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import com.motollantas.MotoLlantasVirtual.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class registerServiceImpl implements registerService {

    @Autowired
    private registerDao registerDao;

    @Autowired
    private DocumentTypeDao documentTypeDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        // Normalizaciones básicas
        user.setFullName(user.getFullName().trim());
        user.setEmail(user.getEmail().trim().toLowerCase());
        if (user.getIdentification() != null) {
            user.setIdentification(user.getIdentification().trim());
        }

        // 1) Validar email único
        if (registerDao.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // 2) Cargar DocumentType completo (el formulario envió solo el id)
        DocumentType type = null;
        if (user.getDocumentType() == null || user.getDocumentType().getId() == null) {
            throw new RuntimeException("Debe seleccionar el tipo de documento.");
        }
        type = documentTypeDao.findById(user.getDocumentType().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento inválido."));
        user.setDocumentType(type); // sustituir el placeholder por la entidad completa

        // 3) Validación del documento contra catálogo
        validateDocument(type, user.getIdentification());

        // 4) Unicidad por (tipo + identificación)
        if (registerDao.existsByDocumentTypeAndIdentification(type, user.getIdentification())) {
            throw new RuntimeException("Ya existe un usuario con ese tipo y número de identificación.");
        }

        // 5) Hash de contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 6) Guardar
        return registerDao.save(user);
    }

    /**
     * Valida la identificación usando las reglas del catálogo (regex/longitudes/isActive).
     */
    private void validateDocument(DocumentType type, String number) {
        if (Boolean.FALSE.equals(type.getIsActive())) {
            throw new RuntimeException("El tipo de documento seleccionado no está activo.");
        }
        if (number == null || number.isBlank()) {
            throw new RuntimeException("Debe ingresar el número de identificación.");
        }
        if (type.getMinLength() != null && number.length() < type.getMinLength()) {
            throw new RuntimeException("La identificación es demasiado corta para " + type.getName() + ".");
        }
        if (type.getMaxLength() != null && number.length() > type.getMaxLength()) {
            throw new RuntimeException("La identificación es demasiado larga para " + type.getName() + ".");
        }
        if (type.getPatternRegex() != null && !type.getPatternRegex().isBlank()) {
            if (!number.matches(type.getPatternRegex())) {
                throw new RuntimeException("El formato de la identificación no es válido para " + type.getName() + ".");
            }
        }
    }
}

