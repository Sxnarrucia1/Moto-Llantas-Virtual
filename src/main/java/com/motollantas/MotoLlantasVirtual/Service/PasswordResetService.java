package com.motollantas.MotoLlantasVirtual.Service;

public interface PasswordResetService {

    void sendResetToken(String email);
    void resetPassword(String token, String newPassword);
    boolean validateToken(String token);

}
