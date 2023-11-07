package org.tema.jwt;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private JwtCore jwtCore;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        UserDetails userDetails = null;

        UsernamePasswordAuthenticationToken authToken = null;

        try {
            String tokenHeader = request.getHeader("Authentication");
            String[] splittedToken = tokenHeader.split(" ",2);
            if(splittedToken[0].equals("Bearer")){
                jwt = splittedToken[1];
                if(jwt != null)
                    username = jwtCore.getUsername(jwt);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    userDetails = userDetailsService.loadUserByUsername(username);
                    authToken = new UsernamePasswordAuthenticationToken(userDetails, null);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        catch (Exception e) {
            //TODO если токен плохой или истек
        }

    }
}
