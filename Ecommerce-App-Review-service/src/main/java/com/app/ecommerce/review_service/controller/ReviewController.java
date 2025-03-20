package com.app.ecommerce.review_service.controller;

import com.app.ecommerce.review_service.dto.request.ReviewRequest;
import com.app.ecommerce.review_service.dto.response.ReviewResponse;
import com.app.ecommerce.review_service.service.specification.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Save review", description = "Save review for given product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review saved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.saveReview(reviewRequest));
    }

    @Operation(summary = "Get reviews for product", description = "Get reviews for given product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsForProduct(@PathVariable("productId") Long productId,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId, page, size));
    }

    @Operation(summary = "Get average rating by product ID", description = "Get average rating for given product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average rating found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/average-rating/{productId}")
    public ResponseEntity<Double> getAverageRatingByProductId(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(reviewService.getAverageRatingByProductId(productId));
    }
}
