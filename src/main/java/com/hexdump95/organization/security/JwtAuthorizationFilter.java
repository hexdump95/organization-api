package com.hexdump95.organization.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hexdump95.organization.services.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtService jwtService;

    public JwtAuthorizationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                DecodedJWT decodedJWT = jwtService.decodeJwt(authHeader);

                List<GrantedAuthority> authorityList =
                        jwtService.getDecodedAuthorities(decodedJWT);

                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authRequest);

            } catch (JWTVerificationException | NullPointerException ex) {
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
