package com.app.ecommerce.review_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@Schema(name = "ReviewResponse", description = "Review Response")
public class ReviewResponse {

    @Schema(name = "productId", description = "productId field for unique product")
    private Long productId;

    @Schema(name = "userEmail", description = "userEmail field for unique user")
    private String userEmail;

    @Schema(name = "rating", description = "rating field for review")
    private Integer rating;

    @Schema(name = "comment", description = "comment field for review")
    private String comment;

    @Schema(name = "createdAt", description = "createdAt field for review")
    private LocalDateTime createdAt;
}
