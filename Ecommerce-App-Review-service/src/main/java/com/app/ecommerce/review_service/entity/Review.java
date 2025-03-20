package com.app.ecommerce.review_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private ObjectId id;

    private Long productId; // ID of the product being reviewed
    private String userEmail; // ID of the user submitting the review
    private Integer rating; // Rating (e.g., 1 to 5)
    private String comment; // Review comment

    private LocalDateTime createdAt;
}