package com.example.erasmus_app.payload.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryUploadResponse {
    private String message;

    public CountryUploadResponse(String message) {
        this.message = message;
    }
}
