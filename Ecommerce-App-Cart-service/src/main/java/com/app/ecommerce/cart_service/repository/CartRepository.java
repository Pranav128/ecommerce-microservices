package com.app.ecommerce.cart_service.repository;

import com.app.ecommerce.cart_service.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserEmail(String userEmail);
}
