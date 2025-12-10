package com.motollantas.MotoLlantasVirtual.handler;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

        String email = request.getParameter("username"); // Spring usa "username"

        User user = userDao.findByEmail(email).orElse(null);

        if (user != null) {

            // si la cuenta no está bloqueada
            if (user.getLockTime() == null) {
                userService.increaseFailedAttempts(user);

                if (user.getFailedAttempts() >= 5) {
                    userService.lock(user);
                    request.getSession().setAttribute("error",
                            "Tu cuenta ha sido bloqueada por múltiples intentos fallidos. Intenta en 5 minutos.");
                } else {
                    request.getSession().setAttribute("error",
                            "Credenciales incorrectas. Intento " + user.getFailedAttempts() + " de 5.");
                }

            } else {
                request.getSession().setAttribute("error",
                        "Tu cuenta está bloqueada. Intenta más tarde.");
            }
        } else {
            // usuario no existe
            request.getSession().setAttribute("error", "El correo no está registrado.");
        }

        response.sendRedirect("/login");
    }
}