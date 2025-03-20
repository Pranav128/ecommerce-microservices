package com.app.ecommerce.product_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Product Request Dto", description = "Product Request Dto")
public class ProductRequestDto {
    @Schema(name = "id", description = "id field for unique product")
    private Long id;

    @Schema(name = "name", description = "name field for product")
    @NotNull(message = "Product name is required")
    private String name;

    @Schema(name = "description", description = "description field for product")
    @NotNull(message = "Product description is required")
    private String description;

    @Schema(name = "price", description = "price field for product")
    @NotNull(message = "Product price is required")
    private Double price;

    @Schema(name = "stock", description = "stock field for product")
    @NotNull(message = "Product stock is required")
    @Min(value = 1, message = "Product stock must be at least 1")
    private Integer stock;

    @Schema(name = "categoryId", description = "categoryId field for product")
    @NotNull(message = "Product category required")
    private Long categoryId;
}
