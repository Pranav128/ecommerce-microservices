package com.app.ecommerce.payment_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Payment Request", description = "Payment Request")
public class PaymentRequest {

    @Schema( name = "orderId",description = "Order ID", example = "1")
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @Schema( name = "amount",description = "Amount", example = "100.00")
    @NotNull(message = "Amount is required")
    private Double amount;

    @Schema( name = "status",description = "Status", example = "PAID")
    private String status;
}
