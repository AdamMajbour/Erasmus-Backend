package com.example.erasmus_app.repositories;

import com.example.erasmus_app.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByEmailAndInstitution(String email, String institution);
}
