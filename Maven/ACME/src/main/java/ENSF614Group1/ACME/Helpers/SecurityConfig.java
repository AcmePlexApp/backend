package ENSF614Group1.ACME.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ENSF614Group1.ACME.Service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
		
	@Autowired private JWTAuthFilter jwtAuthFilter;
	
	@Bean
    @Order(1)
    public SecurityFilterChain adminAndManagementSecurityFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable())
            .securityMatcher("/auth/login", "auth/create")
            .authorizeHttpRequests(authorize -> authorize
            		.anyRequest().permitAll()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add custom filter
       
        return http.build();
    }

    // SecurityFilterChain for Public API
    @Bean
    @Order(2)
    public SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/theater", "/theater/**", "/movie", "/movie/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Allow all requests without authentication
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
        	.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add custom filter
        
        return http.build();
    }

    // SecurityFilterChain for All Other Requests
    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
        	.securityMatcher("/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated() // Require authentication for all other URLs
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add custom filter
        
        return http.build();
    }
    

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(jwtAuthFilter.getUserDetailsService())
                   .and()
                   .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

