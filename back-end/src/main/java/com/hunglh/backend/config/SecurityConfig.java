package com.hunglh.backend.config;

import com.hunglh.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.hunglh.backend.enums.Roles.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
         http.csrf().disable()
                 .authorizeHttpRequests()
                 .requestMatchers("/**")
                 .permitAll()
                 .requestMatchers("/profile/**").authenticated()
                 .requestMatchers("/order/**").authenticated()
                 .requestMatchers("/profiles/**").authenticated()
                 .requestMatchers("/cart/**").hasRole((USER.name()))
                 .requestMatchers("/cart/**").hasRole((ADMIN.name()))
                 .requestMatchers("/order/deliver/**").hasRole(ADMIN.name())
                 .requestMatchers("/order/cancel/**").hasRole(USER.name())
                 .requestMatchers("/order/cancel/**").hasRole(ADMIN.name())
                 .requestMatchers("/order/finish/**").hasRole(USER.name())
                 .requestMatchers("/seller/product/new").hasRole(ADMIN.name())
                 .requestMatchers("/seller/**/delete").hasRole(ADMIN.name())
                 .requestMatchers("/seller/**").hasRole(EMPLOYEE.name())
                 .requestMatchers("/seller/**").hasRole(ADMIN.name())
                 .anyRequest()
                 .authenticated()
                 .and()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
