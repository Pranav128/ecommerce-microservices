package com.app.ecommerce.notification_service.controller;

import com.app.ecommerce.notification_service.dto.request.OrderConfirmationRequest;
import com.app.ecommerce.notification_service.dto.request.PaymentFailureRequest;
import com.app.ecommerce.notification_service.dto.request.PaymentSuccessRequest;
import com.app.ecommerce.notification_service.service.implementation.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "notifications", description = "Notifications API")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    //sync communication to send emails

    @Operation(
            summary = "Send order confirmation email",
            description = "Sends an email to confirm an order",
            tags = "notifications",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping("/order-confirmation")
    public ResponseEntity<Void> sendOrderConfirmationEmail(@RequestBody @Valid OrderConfirmationRequest request) {
        notificationService.sendOrderConfirmationEmail(request.getEmail(), request.getOrderId());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Send payment success email",
            description = "Sends an email to confirm a successful payment",
            tags = "notifications",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping("/payment-success")
    public ResponseEntity<Void> sendPaymentSuccessEmail(@RequestBody @Valid PaymentSuccessRequest request) {
        notificationService.sendPaymentSuccessEmail(request.getEmail(), request.getOrderId());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Send payment failure email",
            description = "Sends an email to notify of a failed payment",
            tags = "notifications",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping("/payment-failure")
    public ResponseEntity<Void> sendPaymentFailureEmail(@RequestBody @Valid PaymentFailureRequest request) {
        notificationService.sendPaymentFailureEmail(request.getEmail(), request.getOrderId());
        return ResponseEntity.ok().build();
    }
}
