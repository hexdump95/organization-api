package com.hexdump95.organization.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hexdump95.organization.services.JwtService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager manager, JwtService jwtService) {
        super(manager);
        this.jwtService = jwtService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        LoginResponse loginResponse = jwtService.createLoginResponse(authResult);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(mapper.writeValueAsString(loginResponse));
    }

}
