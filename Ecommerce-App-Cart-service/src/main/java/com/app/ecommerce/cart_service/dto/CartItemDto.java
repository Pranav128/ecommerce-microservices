package com.app.ecommerce.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO for a cart item")
public class CartItemDto {

    @Schema(name = "productId" ,description = "Product ID")
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(description = "Quantity of the product")
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
