package com.app.ecommerce.review_service.service.specification;

import com.app.ecommerce.review_service.dto.request.ReviewRequest;
import com.app.ecommerce.review_service.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    ReviewResponse saveReview(ReviewRequest review);
    Page<ReviewResponse> getReviewsByProductId(Long productId, int page, int size);
    Double getAverageRatingByProductId(Long productId);
}
