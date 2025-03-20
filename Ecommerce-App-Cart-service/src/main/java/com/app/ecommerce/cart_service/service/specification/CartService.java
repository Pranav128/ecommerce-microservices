package com.app.ecommerce.cart_service.service.specification;

import com.app.ecommerce.cart_service.dto.CartDto;
import com.app.ecommerce.cart_service.dto.CartItemDto;

public interface CartService {
    CartDto addItemToCart(String userEmail, CartItemDto item);
    CartDto getCartByUser(String userEmail);
    void deleteItemFromCart(String userEmail, Long productId);
    void clearCart(String userEmail);
}
