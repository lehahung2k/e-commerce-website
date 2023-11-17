package com.hunglh.backend.service.impl;

import com.hunglh.backend.entities.Products;
import com.hunglh.backend.entities.Review;
import com.hunglh.backend.exception.ApiRequestException;
import com.hunglh.backend.repository.ProductRepository;
import com.hunglh.backend.repository.ReviewRepository;
import com.hunglh.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hunglh.backend.constaints.MessageConstants.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        Products products = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return products.getReviews();
    }

    @Override
    @Transactional
    public Review addReviewToProduct(Review review, Long productId) {
        Products products = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<Review> reviews = products.getReviews();
        reviews.add(review);
        double totalReviews = reviews.size();
        double sumRating = reviews.stream().mapToInt(Review::getRating).sum();
        products.setRating(sumRating / totalReviews);
        return reviewRepository.save(review);
    }
}
