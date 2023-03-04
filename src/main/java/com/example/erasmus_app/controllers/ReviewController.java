package com.example.erasmus_app.controllers;

import com.example.erasmus_app.models.Review;
import com.example.erasmus_app.payload.request.ReviewDto;
import com.example.erasmus_app.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/review")
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> addRate(@RequestBody ReviewDto reviewDto) {
        try {
            return new ResponseEntity<>(reviewService.addReview(reviewDto), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
