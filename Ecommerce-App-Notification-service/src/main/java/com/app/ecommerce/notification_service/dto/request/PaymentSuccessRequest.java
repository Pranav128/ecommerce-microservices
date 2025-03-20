package com.app.ecommerce.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO for order confirmation request")
public class PaymentSuccessRequest {

    @Schema(description = "Email of the customer",name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "ID of the order",name = "orderId")
    @NotBlank(message = "Order ID is required")
    private String orderId;
}