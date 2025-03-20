package com.app.ecommerce.order_service.service.specification;

import com.app.ecommerce.order_service.dto.request.OrderRequest;
import com.app.ecommerce.order_service.dto.response.OrderResponse;
import com.app.ecommerce.order_service.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<OrderResponse> getOrdersByUserEmail(String userEmail,int page, int size, String sort);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
    void deleteOrder(Long orderId);
    OrderResponse updateOrder(Long id, String status);
    void updateOrderStatus(Order order, String status);
}
