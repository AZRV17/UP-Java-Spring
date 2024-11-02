package com.example.finalproject.service;

import com.example.finalproject.models.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review getReviewById(Long id);

    Review saveReview(Review review);

    void deleteReview(Long id);

    Review updateReview(Review review);
}
