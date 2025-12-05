package com.motollantas.MotoLlantasVirtual.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OtpGateFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();

        if (path.startsWith("/login")
                || path.startsWith("/register")
                || path.startsWith("/otp")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.startsWith("/img")
                || path.startsWith("/error")
                || path.equals("/")
                || path.startsWith("/index")
                || path.startsWith("/about")
                || path.startsWith("/location")
                || path.startsWith("/catalog")
                || path.startsWith("/cart")
                || path.startsWith("/errores")) {
            chain.doFilter(req, res);
            return;
        }

        if (req.getUserPrincipal() == null) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession(false);
        Boolean otpOk = session != null ? (Boolean) session.getAttribute("OTP_OK") : null;

        if (otpOk == null || !otpOk) {
            res.sendRedirect("/otp");
            return;
        }

        chain.doFilter(req, res);
    }
}
