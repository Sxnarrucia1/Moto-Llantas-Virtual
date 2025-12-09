package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.PasswordResetService;
import com.motollantas.MotoLlantasVirtual.dao.PasswordResetTokenDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.PasswordResetToken;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenDao tokenDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailServiceImpl emailService;

    @Value("${app.base-url}")
    private String baseUrl;

    private static final long EXPIRATION_MINUTES = 30;

    @Override
    public void sendResetToken(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Correo no registrado"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        tokenDao.save(resetToken);

        String link  = baseUrl + "/resetPassword?token=" + token;
        emailService.sendSimpleEmail(user.getEmail(), "Recuperación de contraseña",
                "Haz clic en el siguiente enlace para restablecer tu contraseña: " + link);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenDao.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado o ya usado");
        }

        validatePassword(newPassword);

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);

        resetToken.setUsed(true);
        tokenDao.save(resetToken);
    }

    @Override
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenDao.findByToken(token);
        return resetToken.isPresent() && !resetToken.get().isUsed() && resetToken.get().getExpiresAt().isAfter(LocalDateTime.now());
    }

    private void validatePassword(String password) {
        if (password.length() < 12 || password.length() > 18) {
            throw new IllegalArgumentException("La contraseña debe tener entre 12 y 18 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un número.");
        }
    }


}
