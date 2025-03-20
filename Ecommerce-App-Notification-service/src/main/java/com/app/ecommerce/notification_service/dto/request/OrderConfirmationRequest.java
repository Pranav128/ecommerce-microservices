package com.app.ecommerce.notification_service.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(name = "OrderConfirmationRequest", description = "DTO for order confirmation request")
public class OrderConfirmationRequest {

    @Schema(name = "email" ,description = "Email of the customer")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema( name = "orderId",description = "ID of the order")
    @NotBlank(message = "Order ID is required")
    private String orderId;
}