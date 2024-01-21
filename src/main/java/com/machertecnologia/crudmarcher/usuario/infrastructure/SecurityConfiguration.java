package com.machertecnologia.crudmarcher.usuario.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    private String authRegisterPath = "/auth/register";
    private String authLoginPath = "/auth/login";
    private String userPath = "/usuarios";

    private String swaggerPath = "/swagger-ui.html";
    private String webjarsPath = "/webjars/**";
    private String swaggerUiPath = "/swagger-ui/index.html";

    private String swaggerApiDocsPath = "/v3/api-docs";
    private String configurationUiPath = "/configuration/ui";
    private String swaggerResourcesPath = "/swagger-resources/**";
    private String configurationSecurityPath = "/configuration/security";
    private String swaggerUiHtmlPath = "/swagger-ui.html";
    private String swaggerUiStarPath = "/swagger-ui/*";
    private String v3Path = "/v3/**";
    private String role = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,
                                swaggerApiDocsPath, webjarsPath, swaggerUiPath,
                                configurationUiPath, swaggerResourcesPath,
                                configurationSecurityPath, swaggerUiHtmlPath,
                                swaggerUiStarPath, v3Path, swaggerPath).permitAll()
                        .requestMatchers(HttpMethod.POST, authRegisterPath).permitAll()
                        .requestMatchers(HttpMethod.POST, authLoginPath).permitAll()
                        .requestMatchers(HttpMethod.POST, userPath).hasRole(role)
                        .requestMatchers(HttpMethod.DELETE, userPath).hasRole(role)
                        .requestMatchers(HttpMethod.PUT, userPath).hasRole(role)
                        .requestMatchers(HttpMethod.PATCH, userPath).hasRole(role)
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
