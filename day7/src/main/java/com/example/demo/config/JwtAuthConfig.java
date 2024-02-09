package com.example.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.TokenBlacklist;
import com.example.demo.utils.JwtUtil;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain; 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthConfig extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    //filter chain has 2 parameters
    //within the filed we will be creating 3 fields
    // authHeadder , token and username
    // check if authorization header is present or not
    // check if the header has token or not
    // check for user info in the token

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
        
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String username;
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }
        token=authHeader.substring(7);
        username=jwtUtil.extractUsername(token);
        if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null && !tokenBlacklist.isBlacklisted(token))
        {
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            //check if the token recieved is valid or not
            if(jwtUtil.isTokenValid(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authToken.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response); 
        } 
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
