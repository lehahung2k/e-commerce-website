package com.hunglh.backend.service;

import com.hunglh.backend.entities.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByProductId(Long productId);

    Review addReviewToProduct(Review review, Long productId);
}
