package com.app.ecommerce.order_service.service.implmentation;

import com.app.ecommerce.order_service.dto.request.OrderItemRequest;
import com.app.ecommerce.order_service.dto.request.OrderRequest;
import com.app.ecommerce.order_service.dto.response.OrderResponse;
import com.app.ecommerce.order_service.entity.Order;
import com.app.ecommerce.order_service.entity.OrderItem;
import com.app.ecommerce.order_service.exception.OrderException;
import com.app.ecommerce.order_service.repository.OrderRepository;
import com.app.ecommerce.order_service.service.specification.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Page<OrderResponse> getOrdersByUserEmail(String userEmail, int page, int size, String sort) {
        Page<Order> orders = orderRepository.findByUserEmail(userEmail, createPageable(page, size, sort));
        return orders.map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserEmail(orderRequest.getUserEmail());
        order.setStatus("PENDING");

        List<OrderItem> items = orderRequest.getItems().stream()
                .map(item -> {
                    OrderItem orderItem = mapToOrderItem(item);
                    orderItem.setOrder(order); // Set the parent reference
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(items);

        orderRepository.save(order);
        //update above line later to send order to payment service
        updateOrderStatus(order, "PENDING");
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order not found"));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderResponse updateOrder(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order not found"));
        order.setStatus(status);
        updateOrderStatus(order,status);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public void updateOrderStatus(Order order, String status) {
        Map<String, Object> message = new HashMap<>();
        message.put("email", order.getUserEmail());
        message.put("status", status);
        message.put("orderId", order.getId().toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String messageString = null;
        try {
            messageString = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert payment update to JSON", e);
        }

        log.info("Sending order status update(messageString/ObjectMapper): {}", messageString +" : "+ getClass().getSimpleName() );

        rabbitTemplate.convertAndSend("order-status-update", messageString);
    }

    private OrderItem mapToOrderItem(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(itemRequest.getProductId());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setPrice(itemRequest.getPrice());
        return orderItem;
    }

    private Pageable createPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Sort sortObj = Sort.by(direction, sortParams[0]);
        return PageRequest.of(page, size, sortObj);
    }
}
