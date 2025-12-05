package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.OtpService;
import com.motollantas.MotoLlantasVirtual.ServiceImpl.EmailServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OtpController {

    private final OtpService otpService;
    private final EmailServiceImpl emailService;

    public OtpController(OtpService otpService, EmailServiceImpl emailService) {
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @GetMapping("/otp")
    public String otpPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("PRE_AUTH_EMAIL");
        if (email == null) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "login/otp";
    }

    @PostMapping("/otp/verify")
    public String verifyOtp(@RequestParam String code,
                            HttpSession session,
                            Model model) {

        String email = (String) session.getAttribute("PRE_AUTH_EMAIL");
        if (email == null) return "redirect:/login";

        boolean ok = otpService.validateOtp(email, code);

        if (!ok) {
            model.addAttribute("email", email);
            model.addAttribute("error", "Código incorrecto o expirado.");
            return "login/otp";
        }

        session.setAttribute("OTP_OK", true);
        session.removeAttribute("PRE_AUTH_EMAIL");

        return "redirect:/";
    }

    @PostMapping("/otp/resend")
    public String resendOtp(HttpSession session, Model model) {
        String email = (String) session.getAttribute("PRE_AUTH_EMAIL");
        if (email == null) return "redirect:/login";

        String otp = otpService.generateOtpFor(email);
        emailService.sendOtpEmail(email, otp);

        model.addAttribute("email", email);
        model.addAttribute("success", "Se envió un nuevo código.");
        return "login/otp";
    }
}
