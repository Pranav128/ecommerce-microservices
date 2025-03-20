package com.app.ecommerce.cart_service.service.implementation;

import com.app.ecommerce.cart_service.dto.CartDto;
import com.app.ecommerce.cart_service.dto.CartItemDto;
import com.app.ecommerce.cart_service.entity.Cart;
import com.app.ecommerce.cart_service.entity.CartItem;
import com.app.ecommerce.cart_service.exception.CartException;
import com.app.ecommerce.cart_service.repository.CartRepository;
import com.app.ecommerce.cart_service.service.specification.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(String userEmail, CartItemDto item) {
        // Validate input
        if (item == null || item.getProductId() == null) {
            throw new IllegalArgumentException("Cart item or product ID cannot be null");
        }

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserEmail(userEmail);
                    newCart.setItems(new ArrayList<>()); // Initialize the items list
                    return newCart;
                });

        // Add or update item in the  cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(item.getProductId());
            newItem.setQuantity(item.getQuantity());
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto getCartByUser(String email) {
        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new CartException("Cart not found"));
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void deleteItemFromCart(String userEmail, Long productId) {
        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userEmail) {
        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new CartException("Cart not found"));
        cartRepository.delete(cart);
    }
}
