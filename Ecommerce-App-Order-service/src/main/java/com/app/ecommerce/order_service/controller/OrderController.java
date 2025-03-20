package com.app.ecommerce.order_service.controller;

import com.app.ecommerce.order_service.dto.request.OrderRequest;
import com.app.ecommerce.order_service.dto.response.OrderResponse;
import com.app.ecommerce.order_service.service.specification.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get orders by user email", description = "Returns the orders for the given user email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Orders not found", content = @Content)
    })
    public ResponseEntity<Page<OrderResponse>> getOrdersByUserEmail(@RequestHeader(value = "X-User-Email") String userEmail,
                                                                   @RequestParam(defaultValue = "0", required = false) int page,
                                                                   @RequestParam(defaultValue = "10", required = false) int size,
                                                                   @RequestParam(defaultValue = "id,desc", required = false) String sort) {
        return ResponseEntity.ok(orderService.getOrdersByUserEmail(userEmail, page, size, sort));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Returns the order for the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Updates the order for the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id, @RequestParam(name = "status") String status) {
        return ResponseEntity.ok(orderService.updateOrder(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Deletes the order for the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}