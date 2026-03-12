package com.example.auth_app_backend.security;

import com.example.auth_app_backend.Utils.UserHelper;
import com.example.auth_app_backend.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    private Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/auth/v1/login") || path.equals("/auth/v1/register")) {
            filterChain.doFilter(request, response);
            return;
        }
      String header= request.getHeader("Authorization");
      logger.info("Authorization : {}",header);
       if(header!=null && header.startsWith("Bearer")){
           //token extract validate  then authenticate create and then set in  security context
           String token=header.substring(7);

           try{


               //check for accessToken
               if(!jwtService.isAccessToken(token)){
                   filterChain.doFilter(request,response);
                   return;
               }
              Jws<Claims>parse= jwtService.parse(token);
              Claims payload=parse.getPayload();
              String userId= payload.getSubject();
               UUID userUuid= UserHelper.parseUUID(userId);
               userRepository.findById(userUuid).ifPresent( user ->{
                   //check for userEnable or not
                   if(!user.isEnabled()){
                       try {
                           filterChain.doFilter(request,response);
                       } catch (IOException | ServletException e) {
                           throw new RuntimeException(e);
                       }
                       return;
                   }
                   List<GrantedAuthority> authorities =
                           user.getRoles().stream()
                                   .map(role -> new SimpleGrantedAuthority(role.getName()))
                                   .collect(Collectors.toUnmodifiableList());

                   UsernamePasswordAuthenticationToken token1=new UsernamePasswordAuthenticationToken(user.getEmail(),null,authorities);
                   token1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   //final line : set authentication to security context
                   if(SecurityContextHolder.getContext().getAuthentication()==null) {
                       SecurityContextHolder.getContext().setAuthentication(token1);
                   }

               });
           }
           catch (ExpiredJwtException e){
           e.printStackTrace();
           }
               catch (MalformedJwtException e) {
               e.printStackTrace();
           }
           catch (JwtException e){
               e.printStackTrace();
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }
       filterChain.doFilter(request,response);
    }
    protected boolean shouldNotFilter(HttpServletRequest request)throws ServletException{
        return  request.getRequestURI().startsWith("/auth/v1/");
    }
}
