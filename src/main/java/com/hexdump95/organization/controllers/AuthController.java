package com.hexdump95.organization.controllers;

import com.hexdump95.organization.security.LoginRequest;
import com.hexdump95.organization.security.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth")
@RestController
public class AuthController {
    public final static String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @PostMapping(path = LOGIN_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        throw new UnsupportedOperationException();
    }
}
