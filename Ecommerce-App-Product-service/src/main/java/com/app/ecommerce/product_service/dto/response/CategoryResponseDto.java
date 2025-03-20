package com.app.ecommerce.product_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CategoryResponse", description = "Category response dto")
public class CategoryResponseDto {

    @Schema(name = "id", description = "id field for unique category", example = "1")
    private Long id;

    @Schema(name = "name", description = "name field for category", example = "Electronics")
    private String name;
}