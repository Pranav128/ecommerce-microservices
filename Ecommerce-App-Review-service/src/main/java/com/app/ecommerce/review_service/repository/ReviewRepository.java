package com.app.ecommerce.review_service.repository;

import com.app.ecommerce.review_service.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable); // Find reviews by product ID
    @Aggregation(pipeline = {
            "{ $match: { productId: ?0 } }", // Match reviews for the given productId
            "{ $group: { _id: null, averageRating: { $avg: '$rating' } } }" // Calculate average rating
    })
    Double findAverageRatingByProductId(Long productId); // Calculate average rating for a product

}