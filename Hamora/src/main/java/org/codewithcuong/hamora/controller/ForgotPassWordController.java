package org.codewithcuong.hamora.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;
import org.codewithcuong.hamora.service.EmailService;

@Controller
public class ForgotPassWordController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "page/forgotPassword";
    }

    // Xử lý form submit
    @PostMapping("/forgotPassword")
    public String processForgotPassword(HttpServletRequest request, @RequestParam("email") String email, Model model) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return "redirect:/forgotPassword?error";
        }

        String token = java.util.UUID.randomUUID().toString();
        userRepo.savePasswordResetToken(user.getId(), token);

        try {
            emailService.sendResetPasswordEmail(email, token);
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace();
            return "redirect:/forgotPassword?mailerror";
        }


        return "redirect:/forgotPassword?success";
    }

    @GetMapping("/resetPassword")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        User user = userRepo.findUserByToken(token);
        if (user == null) {
            return "redirect:/forgotPassword?invalid";
        }
        model.addAttribute("token", token);
        return "page/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processReset(@RequestParam("token") String token,
                               @RequestParam("password") String password) {
        User user = userRepo.findUserByToken(token);
        if (user == null) {
            return "redirect:/forgotPassword?invalid";
        }

        // Hash password (bạn nên dùng BCrypt)
        String hashed = passwordEncoder.encode(password);
        userRepo.updatePassword(user.getId(), hashed);
        userRepo.deleteToken(token);

        return "redirect:/login?resetSuccess";
    }

}
