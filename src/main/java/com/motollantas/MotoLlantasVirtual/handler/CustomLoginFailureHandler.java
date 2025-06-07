package com.motollantas.MotoLlantasVirtual.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage;

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "El correo no está registrado.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Las credenciales son incorrectas.";
        } else {
            errorMessage = "Error al iniciar sesión.";
        }

        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/login");
    }
}