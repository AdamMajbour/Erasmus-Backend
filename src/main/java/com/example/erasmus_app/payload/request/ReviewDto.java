package com.example.erasmus_app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String email;
    private String country;
    private String city;
    private String institution;
    private float rateCity;
    private float rateInstitution;
    private String textExplanation;
}
