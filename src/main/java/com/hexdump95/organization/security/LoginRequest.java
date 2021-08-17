package com.hexdump95.organization.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
