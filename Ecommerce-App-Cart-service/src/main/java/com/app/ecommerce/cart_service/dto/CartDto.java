package com.app.ecommerce.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "CartDto")
public class CartDto{
    @Schema(name = "userEmail")
    @NotNull(message = "User email is required")
    @NotBlank(message = "User email is required")
    String userEmail;

    @Schema(name = "items")
    @NotNull(message = "Items are required")
    List<CartItemDto> items;
}