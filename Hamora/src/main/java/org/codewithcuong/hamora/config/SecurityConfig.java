package org.codewithcuong.hamora.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.codewithcuong.hamora.handler.FormLoginSuccessHandler;
import org.codewithcuong.hamora.handler.OAuth2LoginSuccessHandler;
import org.codewithcuong.hamora.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private FormLoginSuccessHandler formLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/files/**", "/webhook") // ✅ thêm webhook ở đây
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login", "/register", "/forgot-password",
                                "/css/**", "/js/**", "/images/**", "/assets/**",
                                "/api/files/**" //  Allow API access
                        ).permitAll()
                        .requestMatchers("/webhook").permitAll()
                        .requestMatchers("/user-profile", "/update-user-profile").authenticated() // Chỉ cho phép người đã xác thực
                        .anyRequest().permitAll() // in dev mode
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .successHandler(formLoginSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .rememberMe(r -> r
                        .key("bKJHkjsdf8723hJKH8sd89fjsd0239JKLHkjasdf987sdf")
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 7 ngày
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler)
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
