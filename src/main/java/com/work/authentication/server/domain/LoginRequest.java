package com.work.authentication.server.domain;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
