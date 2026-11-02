package com.ujjwal.springsecurity.filters;

import com.ujjwal.springsecurity.service.CustomUserDetailsService;
import com.ujjwal.springsecurity.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetch user details from the database and validate the token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            boolean valid = jwtUtil.validateToken(token, userDetails, username);
            if(valid){
                // If the token is valid, set the authentication in the SecurityContextHolder
                // You can create an authentication object and set it in the SecurityContextHolder
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // If the token is valid, set the authentication in the SecurityContextHolder
        filterChain.doFilter(request, response);

    }
}
