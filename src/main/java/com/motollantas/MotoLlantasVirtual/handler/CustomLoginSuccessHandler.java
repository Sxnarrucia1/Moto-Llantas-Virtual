package com.motollantas.MotoLlantasVirtual.handler;

import com.motollantas.MotoLlantasVirtual.Service.OtpService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.ServiceImpl.EmailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final EmailServiceImpl emailService;
    private final OtpService otpService;

    @Autowired
    private UserService userService;

    public CustomLoginSuccessHandler(EmailServiceImpl emailService, OtpService otpService) {
        this.emailService = emailService;
        this.otpService = otpService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String email = authentication.getName();

        userService.resetFailedAttempts(userService.findByEmail(email));

        if (request.getSession().getAttribute("PRE_AUTH_EMAIL") == null) {

            String otp = otpService.generateOtpFor(email);
            emailService.sendOtpEmail(email, otp);

            request.getSession().setAttribute("PRE_AUTH_EMAIL", email);
        }

        response.sendRedirect("/otp");
    }
}
