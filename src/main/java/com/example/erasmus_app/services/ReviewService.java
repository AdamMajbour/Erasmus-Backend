package com.example.erasmus_app.services;

import com.example.erasmus_app.Exceptions.AlreadyExistException;
import com.example.erasmus_app.models.Review;
import com.example.erasmus_app.payload.request.ReviewDto;
import com.example.erasmus_app.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public String addReview(ReviewDto reviewDto) {
        if (reviewRepository.existsByEmailAndInstitution(reviewDto.getEmail(), reviewDto.getInstitution()))
            throw new AlreadyExistException(HttpStatus.CONFLICT, "You already reviewed this institution!");

        reviewRepository.save(Review.builder().email(reviewDto.getEmail()).country(reviewDto.getCountry())
                .city(reviewDto.getCity()).rateCity(reviewDto.getRateCity())
                .rateInstitution(reviewDto.getRateInstitution()).build());

        return "Review Submitted!";
    }
}
