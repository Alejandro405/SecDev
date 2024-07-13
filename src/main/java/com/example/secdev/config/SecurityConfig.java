package com.example.secdev.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;



    @Bean
    public DaoAuthenticationProvider jpaDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);


        return provider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(jpaDaoAuthenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // For Postman
                .authorizeHttpRequests(auth -> auth  // manage access
                        .requestMatchers(HttpMethod.POST,  "/actuator/shutdown").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyAuthority("USER", "ACCOUNTANT")
                        .requestMatchers(HttpMethod.POST, "/api/auth/changepass").hasAnyAuthority("USER", "ACCOUNTANT", "ADMIN")
                        .requestMatchers( "/api/acct/payments").hasAuthority("ACCOUNTANT")
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        // other matchers
                        .requestMatchers("/error").authenticated()
                        .anyRequest().denyAll()
                )
                .sessionManagement(sessions -> sessions
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                );

        return http.build();
    }




}
