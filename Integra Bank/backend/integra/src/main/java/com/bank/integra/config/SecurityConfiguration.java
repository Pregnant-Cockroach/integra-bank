package com.bank.integra.config;

import com.bank.integra.services.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/home").hasRole("EMPLOYEE")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/styles/login/**").permitAll()
                                .requestMatchers("/styles/logo/**").permitAll()
                                .requestMatchers("/integra_login2").permitAll()
                                .requestMatchers("/integra_login1").permitAll()
                                .requestMatchers("/staffLogin").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .usernameParameter("id")
                                .defaultSuccessUrl("/redirect", true)
                                .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .userDetailsService(userDetailsService); // Используем CustomUserDetailsService
        return httpSecurity.build();
    }
}