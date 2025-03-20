package com.app.ecommerce.order_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "OrderResponse", description = "Order Response DTO")
public class OrderResponse {

    @Schema( name = "id", description = "id field for unique order" )
    private Long id;

    @Schema( name = "userEmail", description = "userEmail field for unique user" )
    private String userEmail;

    @Schema( name = "items", description = "list of Order Items for order" )
    private List<OrderItemResponse> items;

    @Schema( name = "status", description = "status field for order" )
    private String status;
}