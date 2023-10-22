package com.library.libraryservice.security_config;

import com.library.libraryservice.db2.entity.User;
import com.library.libraryservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            username = jwtHelper.getUsernameFromToken(token);
            System.out.println("username from token ->"+username);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserByUsername(username);
                System.out.println("user found -> "+user);
                Boolean validToken = jwtHelper.validateToken(token, user);
                System.out.println("is valid token->"+validToken);
                Collection<? extends GrantedAuthority> authorities =
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
                if(validToken) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(user, null, authorities);
//                            authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
