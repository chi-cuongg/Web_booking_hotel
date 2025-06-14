package org.codewithcuong.hamora.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;
import org.codewithcuong.hamora.service.EmailService;

@Controller
public class RegisterController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String registerForm() {
        return "page/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("fullname") String fullname,
            Model model,
            HttpSession session) {


        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            model.addAttribute("error", "All fields are required.");
            return "page/register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "page/register";
        }

        if (userRepo.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists.");
            return "page/register";
        }

        if (!fullname.matches("^[\\p{L} '\\-]+$")) {
            model.addAttribute("error", "Full name must not contain special characters.");
            return "page/register";
        }

        String hashedPassword = passwordEncoder.encode(password);
        User existingUser = userRepo.findByEmail(email);

        if (existingUser != null) {
            model.addAttribute("error", "Email already exists.");
            return "page/register";
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        emailService.sendOtpEmail(email, otp);
        userRepo.saveEmailOtpToken(email, otp);

        User user = new User(email, hashedPassword);
        user.setFullname(fullname);
        session.setAttribute("user", user);

        return "page/verify-email-otp";

    }
}
