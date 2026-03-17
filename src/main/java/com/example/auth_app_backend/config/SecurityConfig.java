package com.example.auth_app_backend.config;

import com.example.auth_app_backend.repositories.UserRepository;
import com.example.auth_app_backend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 @Configuration
@EnableWebSecurity


 // oAuth 2 login uRl---->  http://localhost:8080/oauth2/authorization/google
public class SecurityConfig {
//    @Bean
//    UserDetailsService users(){
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        UserDetails user1=userBuilder.username("Rama").password("abc").roles("Admin").build();
//        return new InMemoryUserDetailsManager(user1);
//    }
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
     UserRepository userRepository;
    @Autowired
     AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/auth/v1/register",
                                "/auth/v1/login",
                                "/oauth2/**",
                                "/auth/v1/refresh","/auth/v1/logoutRefreshToken"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET).hasRole(APPConstants.GUEST_ROLE)
                        .requestMatchers("/api/v1/users").hasRole(APPConstants.ADMIN_ROLE)
                        .anyRequest().authenticated()
                ).oauth2Login(oauth2->
                        oauth2.successHandler(authenticationSuccessHandler)
                        .failureHandler(null))
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"error\":\"Unauthorized\",\"message\":\"Invalid or Missing Token\"}"
                            );
                        })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();



    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
     @Bean
     public AuthenticationManager authenticationManager(
             AuthenticationConfiguration configuration) throws Exception {
         return configuration.getAuthenticationManager();
     }



}
