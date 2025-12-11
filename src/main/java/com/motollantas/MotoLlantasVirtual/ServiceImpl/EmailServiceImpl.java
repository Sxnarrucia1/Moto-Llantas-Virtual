package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private final Resend resend;

    public EmailServiceImpl(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    // Enviar credenciales
    public void sendCredentialsEmail(String to, String tempPassword) {

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("MotoLlantas <no-reply@motollantavirtual.xyz>")
                .to(to)
                .subject("Credenciales de Acceso")
                .html("<h2>Bienvenido a MotoLlantas</h2>" +
                        "<p>Tu contraseña temporal es:</p>" +
                        "<h3 style='color:#333;'>" + tempPassword + "</h3>" +
                        "<p>Por seguridad, debes cambiarla al iniciar sesión.</p>")
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("Correo enviado correctamente. ID: " + response.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error enviando el correo: " + e.getMessage());
        }
    }

    // Enviar OTP
    public void sendOtpEmail(String to, String otp) {

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("MotoLlantas <no-reply@motollantavirtual.xyz>")
                .to(to)
                .subject("Código de Verificación (OTP)")
                .html("<h2>Verificación de Seguridad</h2>" +
                        "<p>Tu código OTP es:</p>" +
                        "<h1 style='letter-spacing: 4px; color:#F47808;'>" + otp + "</h1>" +
                        "<p>Este código expira en 5 minutos.</p>")
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("OTP enviado correctamente. ID: " + response.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error enviando el OTP: " + e.getMessage());
        }
    }

    public void sendSimpleEmail(String to, String subject, String htmlMessage) {

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("MotoLlantas <no-reply@motollantavirtual.xyz>")
                .to(to)
                .subject(subject)
                .html(htmlMessage)
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("Correo enviado correctamente. ID: " + response.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error enviando el correo: " + e.getMessage());
        }
    }
}






