package org.polytech.covidapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.polytech.covidapi.config.jwt.AuthEntryPointJwt;
import org.polytech.covidapi.config.jwt.AuthTokenFilter;
import org.polytech.covidapi.entities.ERole;
import org.polytech.covidapi.services.UtilisateurService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(utilisateurService);
        authProvider.setPasswordEncoder(passwordEncoder());
    
        return authProvider;
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
        .requestMatchers("/api/auth/login").permitAll()
        .requestMatchers("/api/auth/register").permitAll()
        .requestMatchers("/api/auth/**").authenticated()
        .requestMatchers("/api/user/admin/**").hasAuthority(ERole.ADMIN.toString())
        .requestMatchers("/api/user/**").authenticated()
        .requestMatchers("/api/center/getAll").permitAll()
        .requestMatchers("/api/center/getByCity").permitAll()
        .requestMatchers("/api/center/getById/**").permitAll()
        .requestMatchers("/api/center/**").hasAuthority(ERole.ADMIN.toString())	
        .requestMatchers("/api/reservation/**").authenticated()
        .requestMatchers("/api/**").hasAuthority(ERole.ADMIN.toString())
        .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
;
        
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
