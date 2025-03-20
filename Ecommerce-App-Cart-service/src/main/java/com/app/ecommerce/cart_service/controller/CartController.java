package com.app.ecommerce.cart_service.controller;

import com.app.ecommerce.cart_service.dto.CartDto;
import com.app.ecommerce.cart_service.dto.CartItemDto;
import com.app.ecommerce.cart_service.service.specification.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart Controller", description = "APIs for managing carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart by user", description = "Returns the cart for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = @Content)
    })
    public ResponseEntity<CartDto> getCartByUser(@RequestHeader("X-User-Email") String email) {
        return new ResponseEntity<>(cartService.getCartByUser(email), HttpStatus.OK);
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart", description = "Adds an item to the cart for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    public ResponseEntity<CartDto> addItemToCart(@RequestHeader("X-User-Email") String email, @RequestBody @Valid CartItemDto item) {
        return new ResponseEntity<>(cartService.addItemToCart(email, item), HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Delete item from cart", description = "Deletes an item from the cart for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted from cart successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found in cart", content = @Content)
    })
    public ResponseEntity<Void> deleteItemFromCart(@RequestHeader("X-User-Email") String email, @PathVariable Long productId) {
        cartService.deleteItemFromCart(email, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Clear cart", description = "Clears the cart for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
    })
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Email") String email) {
        cartService.clearCart(email);
        return ResponseEntity.noContent().build();
    }
}