package com.app.ecommerce.order_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // ID of the product
    private Integer quantity; // Quantity of the product in the order
    private Double price; // Price of the product at the time of order

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}