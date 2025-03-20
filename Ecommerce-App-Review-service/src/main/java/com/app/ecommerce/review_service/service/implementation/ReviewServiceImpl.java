package com.app.ecommerce.review_service.service.implementation;

import com.app.ecommerce.review_service.dto.request.ReviewRequest;
import com.app.ecommerce.review_service.dto.response.ReviewResponse;
import com.app.ecommerce.review_service.entity.Review;
import com.app.ecommerce.review_service.exception.ReviewException;
import com.app.ecommerce.review_service.repository.ReviewRepository;
import com.app.ecommerce.review_service.service.specification.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewResponse saveReview(ReviewRequest review) {
        Review toSave = modelMapper.map(review, Review.class);
        toSave.setCreatedAt(LocalDateTime.now());
        Review saved = reviewRepository.save(toSave);
        return modelMapper.map(saved, ReviewResponse.class);
    }

    @Override
    public Page<ReviewResponse> getReviewsByProductId(Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewRepository.findByProductId(productId, pageable);
        if (reviewPage.isEmpty()) {
            throw new ReviewException("No reviews found for product");
        }
        return reviewPage.map(this::mapToReviewResponse);
    }

    @Override
    public Double getAverageRatingByProductId(Long productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        return modelMapper.map(review, ReviewResponse.class);
    }
}
