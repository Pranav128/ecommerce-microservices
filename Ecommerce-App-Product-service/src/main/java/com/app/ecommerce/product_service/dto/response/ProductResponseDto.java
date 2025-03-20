package com.app.ecommerce.product_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Product Response Dto", description = "Product Response Dto")
public class ProductResponseDto {

    @Schema(name = "id", description = "id field for unique product")
    private Long id;

    @Schema(name = "name", description = "name field for product")
    private String name;

    @Schema(name = "description", description = "description field for product")
    private String description;

    @Schema(name = "price", description = "price field for product")
    private Double price;

    @Schema(name = "stock", description = "stock field for product")
    private Integer stock;

    @Schema(name = "categoryId", description = "categoryId field for product")
    private Long categoryId;
}
