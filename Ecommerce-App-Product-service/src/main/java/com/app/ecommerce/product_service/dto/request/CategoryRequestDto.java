package com.app.ecommerce.product_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(name = "CategoryRequestDto", description = "Category request dto")
public class CategoryRequestDto {
    @NotBlank(message = "Category name is required")
    @NotNull(message = "Category name is required")
    @Schema(name = "name", description = "name field for category", example = "Electronics")
    private String name;
}