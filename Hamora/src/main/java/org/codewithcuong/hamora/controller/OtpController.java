package org.codewithcuong.hamora.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;
import org.codewithcuong.hamora.service.EmailService;

@Controller
public class OtpController {

    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    @PostMapping("/verify-email-otp")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "Session expired. Please register again.");
            return "page/register";
        }

        if (!userRepo.isValidEmailOtp(user.getEmail(), otp)) {
            model.addAttribute("error", "Invalid or expired OTP.");
            return "page/verify-email-otp";
        }

        userRepo.saveUser(user);
        userRepo.deleteEmailOtp(user.getEmail(), otp);
        session.invalidate();

        return "redirect:/login";
    }

    @PostMapping("/resend-otp")
    public String resendOtp(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "Session expired. Please register again.");
            return "page/register";
        }

        // Sinh lại OTP mới
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        // Gửi email
        emailService.sendOtpEmail(user.getEmail(), otp);

        // Lưu OTP vào DB
        userRepo.saveEmailOtpToken(user.getEmail(), otp);

        model.addAttribute("message", "OTP đã được gửi lại đến email của bạn.");
        return "page/verify-email-otp";
    }

}
