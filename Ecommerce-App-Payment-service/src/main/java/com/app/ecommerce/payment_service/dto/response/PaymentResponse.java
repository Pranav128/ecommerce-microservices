package com.app.ecommerce.payment_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Tag(name = "Payment Response")
public class PaymentResponse {

    @Schema( name = "orderId",description = "Order ID", example = "1")
    private Long orderId;

    @Schema( name = "paymentId",description = "Payment ID", example = "1")
    private String paymentId;

    @Schema( name = "paymentMethod",description = "Payment Method", example = "CARD")
    private String paymentMethod;

    @Schema( name = "amount",description = "Amount", example = "100.00")
    private Double amount;

    @Schema( name = "status",description = "Status", example = "PAID")
    private String status;


}