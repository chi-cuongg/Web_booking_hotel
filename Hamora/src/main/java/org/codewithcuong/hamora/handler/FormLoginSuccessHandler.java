package org.codewithcuong.hamora.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            User user = userRepo.findByEmail(userDetails.getUsername());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("name", user.getFullname());
        }

        response.sendRedirect("/home");
    }
}
