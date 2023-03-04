package com.example.erasmus_app.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String name, String lastName, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}
