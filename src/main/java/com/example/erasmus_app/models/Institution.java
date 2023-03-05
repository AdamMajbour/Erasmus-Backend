package com.example.erasmus_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "institution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String name;
    private String description;
    private String imageName;
    private String imageType;
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] image;
    private String imageAddress;
}
