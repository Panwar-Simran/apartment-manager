package com.Simran.apartmentmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ─── PUBLIC ───────────────────────────────────────
                        .requestMatchers("/api/auth/**").permitAll()

                        // ─── MEMBER MANAGEMENT (Pradhana only) ───────────
                        .requestMatchers("/api/members/**")
                        .hasRole("PRADHANA")

                        // ─── MAINTENANCE CYCLE (Pradhana only) ───────────
                        .requestMatchers("/api/maintenance/**")
                        .hasRole("PRADHANA")

                        // ─── EXPENSES ─────────────────────────────────────
                        // Only POST (add) is Pradhana only
                        // GET (view) is both roles
                        .requestMatchers(HttpMethod.POST,
                                "/api/expenses/**")
                        .hasRole("PRADHANA")
                        .requestMatchers(HttpMethod.GET,
                                "/api/expenses/**")
                        .authenticated()

                        // ─── PAYMENTS ─────────────────────────────────────
                        // Approve and Reject → Pradhana only
                        // Upload and View → Both roles
                        .requestMatchers(HttpMethod.PUT,
                                "/api/payments/*/approve",
                                "/api/payments/*/reject")
                        .hasRole("PRADHANA")
                        .requestMatchers("/api/payments/**")
                        .authenticated()

                                // ─── EXTRA CONTRIBUTIONS ──────────────────────────

// 1. Submit → both roles (POST)
                                .requestMatchers(HttpMethod.POST,
                                        "/api/contributions/submit")
                                .authenticated()

                       // 2. Approve, Reject → Pradhana only (PUT)
                                .requestMatchers(HttpMethod.PUT,
                                        "/api/contributions/*/approve",
                                        "/api/contributions/*/reject")
                                .hasRole("PRADHANA")

                                // 3. View ALL → Pradhana only (GET)
                                .requestMatchers(HttpMethod.GET,
                                        "/api/contributions/all",
                                        "/api/contributions/status/*")
                                .hasRole("PRADHANA")

                         // 4. View OWN → both roles (GET)
                                .requestMatchers(HttpMethod.GET,
                                        "/api/contributions/my")
                                .authenticated()

                        // ─── MEETINGS ─────────────────────────────────────
                        // Create and Delete → Pradhana only
                        // View → Both roles
                        .requestMatchers(HttpMethod.POST,
                                "/api/meetings/**")
                        .hasRole("PRADHANA")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/meetings/**")
                        .hasRole("PRADHANA")
                        .requestMatchers(HttpMethod.GET,
                                "/api/meetings/**")
                        .authenticated()

                        // ─── MONTHLY REPORT ───────────────────────────────
                        // Both roles can view
                        .requestMatchers("/api/reports/**")
                        .authenticated()

                        // ─── MEMBER CREDITS ───────────────────────────────
                        // Both roles can view
                        .requestMatchers("/api/credits/**")
                        .authenticated()

                        // ─── ANY OTHER REQUEST ────────────────────────────
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}