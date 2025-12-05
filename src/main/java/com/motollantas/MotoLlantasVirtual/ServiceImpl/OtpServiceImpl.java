package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.OtpService;
import com.motollantas.MotoLlantasVirtual.dao.OtpCodeDao;
import com.motollantas.MotoLlantasVirtual.domain.OtpCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpCodeDao otpDao;
    private final PasswordEncoder encoder;
    private final Random random = new Random();

    public OtpServiceImpl(OtpCodeDao otpDao, PasswordEncoder encoder) {
        this.otpDao = otpDao;
        this.encoder = encoder;
    }

    @Override
    public String generateOtpFor(String email) {
        String code = String.format("%06d", random.nextInt(1_000_000));

        System.out.println("🔐 OTP generado para " + email + ": " + code);

        OtpCode entity = new OtpCode();
        entity.setEmail(email);
        entity.setCodeHash(encoder.encode(code));
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        entity.setUsed(false);

        otpDao.save(entity);

        return code;
    }

    @Override
    public boolean validateOtp(String email, String code) {
        return otpDao
                .findTopByEmailAndUsedFalseOrderByExpiresAtDesc(email)
                .filter(e -> e.getExpiresAt().isAfter(LocalDateTime.now()))
                .filter(e -> encoder.matches(code, e.getCodeHash()))
                .map(e -> {
                    e.setUsed(true);
                    otpDao.save(e);
                    return true;
                })
                .orElse(false);
    }
}
