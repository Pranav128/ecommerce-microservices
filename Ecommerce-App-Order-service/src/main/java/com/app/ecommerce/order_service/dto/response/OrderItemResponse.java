package com.app.ecommerce.order_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Order Item Response", description = "Order Item Response DTO")
public class OrderItemResponse {

    @Schema( name = "productId", description = "productId field for unique product" )
    private Long productId;

    @Schema( name = "quantity", description = "quantity field for product" )
    private Integer quantity;

    @Schema( name = "price", description = "price field for product" )
    private Double price;
}