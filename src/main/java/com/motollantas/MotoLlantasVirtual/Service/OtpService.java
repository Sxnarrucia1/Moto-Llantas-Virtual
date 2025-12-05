package com.motollantas.MotoLlantasVirtual.Service;

public interface OtpService {
    String generateOtpFor(String email);
    boolean validateOtp(String email, String code);
}
