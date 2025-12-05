package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCredentialsEmail(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Credenciales de acceso");
        message.setText("Tu contraseña temporal es: " + password + "\nPor favor cámbiala al iniciar sesión.");
        mailSender.send(message);
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Código de verificación - Moto Llantas Virtual");
        message.setText("Tu código de verificación es: " + otp + "\nEste código vence en 5 minutos.");
        mailSender.send(message);
    }
}
