package com.rpp95.bookstorerest.entities;

import lombok.Data;

@Data
public class LoginRequest {
    
    private String email;
    private String password;
}
