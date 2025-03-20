package com.app.ecommerce.order_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "OrderItemRequest", description = "OrderItemRequest details")
public class OrderItemRequest {
    @Schema( name = "productId", description = "productId field for unique product" )
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema( name = "quantity", description = "quantity field for product" )
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Schema( name = "price", description = "price field for product" )
    @NotNull(message = "Price is required")
    private Double price;
}