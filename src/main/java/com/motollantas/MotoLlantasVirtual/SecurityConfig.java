package com.motollantas.MotoLlantasVirtual;

import com.motollantas.MotoLlantasVirtual.handler.CustomLoginFailureHandler;
import com.motollantas.MotoLlantasVirtual.handler.CustomLoginSuccessHandler;
import com.motollantas.MotoLlantasVirtual.handler.OtpGateFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomLoginFailureHandler failureHandler;
    private final CustomLoginSuccessHandler successHandler;
    private final OtpGateFilter otpGateFilter;

    public SecurityConfig(CustomLoginFailureHandler failureHandler,
                          CustomLoginSuccessHandler successHandler,
                          OtpGateFilter otpGateFilter) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.otpGateFilter = otpGateFilter;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(otpGateFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/index",
                        "/register", "/user/register",
                        "/login", "/otp",
                        "/catalog/**", "/catalog/productDetails/**",
                        "/cart", "/about", "/location",
                        "/css/**", "/js/**", "/img/**",
                        "/error**", "/errores/**"
                ).permitAll()

                .requestMatchers("/employee", "/inventory", "/calendar/**", "/dashboard/finance/**")
                    .hasRole("ADMIN")

                .requestMatchers("/garage/mechanic*")
                    .hasRole("MECANICO")

                .requestMatchers("/trabajador/**")
                    .hasRole("EMPLOYEE")

                .requestMatchers("/garage/userGarage")
                    .hasRole("USUARIO")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
