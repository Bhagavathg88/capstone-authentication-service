package com.services.security.model;

import lombok.Data;

@Data
public class AuthValidationRequest {

    private String token;
    private String userName;

    public AuthValidationRequest(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

    public AuthValidationRequest() {
    }
}
