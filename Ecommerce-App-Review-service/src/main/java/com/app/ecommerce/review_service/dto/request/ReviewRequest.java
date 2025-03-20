package com.app.ecommerce.review_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "ReviewRequest", description = "Review Request")
public class ReviewRequest {
    @Schema(name = "productId", description = "productId field for unique product")
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(name = "userEmail", description = "userId field for unique user")
    @NotNull(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;

    @Schema(name = "rating", description = "rating field for review")
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Schema(name = "comment", description = "comment field for review")
    @NotNull(message = "Comment is required")
    private String comment;
}