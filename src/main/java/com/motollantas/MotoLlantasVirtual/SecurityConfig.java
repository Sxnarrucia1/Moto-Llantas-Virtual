package com.motollantas.MotoLlantasVirtual;

import com.motollantas.MotoLlantasVirtual.handler.CustomLoginFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomLoginFailureHandler failureHandler;

    public SecurityConfig(CustomLoginFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/register", "/user/register", "/catalog/**", "/catalog/productDetails/**", "/cart", "/login",  "/about", "/location", "/css/**", "/error**", "/errores/**", "/img/**", "/js/**").permitAll()
                .requestMatchers("/employee", "/inventory", "/calendar/**", "/dashboard/finance/**").hasRole("ADMIN")
                .requestMatchers("/garage/mechanic*").hasRole("MECANICO")
                .requestMatchers("/trabajador/**").hasRole("EMPLOYEE")
                .requestMatchers("/garage/userGarage").hasRole("USUARIO")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                );

        return http.build();
    }
}
