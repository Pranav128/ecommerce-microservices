package com.app.ecommerce.order_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "OrderRequest", description = "Order request DTO")
public class OrderRequest {
    @Schema( name = "userEmail", description = "userEmail field for unique user" )
    @NotNull(message = "User email is required")
    private String userEmail;

    @Schema( name = "status", description = "status field for order" )
    private String status;

    @Schema( name = "items", description = "items list for order" )
    @NotNull(message = "Items are required")
    private List<OrderItemRequest> items;
}