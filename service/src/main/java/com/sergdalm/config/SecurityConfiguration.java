package com.sergdalm.config;

import com.sergdalm.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration", "v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMINISTRATOR.getAuthority())
                        .antMatchers("/specialist/**").hasAuthority(Role.SPECIALIST.getAuthority())
                        .antMatchers("/admin/**").hasAuthority(Role.ADMINISTRATOR.getAuthority())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
