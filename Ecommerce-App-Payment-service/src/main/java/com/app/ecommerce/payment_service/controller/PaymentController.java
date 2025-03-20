package com.app.ecommerce.payment_service.controller;

import com.app.ecommerce.payment_service.dto.request.PaymentRequest;
import com.app.ecommerce.payment_service.dto.response.PaymentResponse;
import com.app.ecommerce.payment_service.service.specification.PaymentService;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Tag(name = "payments", description = "Payment operations")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Create a payment order",
            description = "Creates a new payment order",
            tags = "payments",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Payment order created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponse> createPaymentOrder(
                                              @RequestParam(name = "orderId") Long orderId,
                                              @RequestParam(name = "amount") Double amount,
                                              @RequestParam(name = "paymentMethod") String paymentMethod) throws RazorpayException {
        return new ResponseEntity<>(paymentService.createPaymentOrder(orderId, amount, paymentMethod), HttpStatus.OK);
    }

    @Operation(
            summary = "Get payment order by ID",
            description = "Returns the payment order for the given ID",
            tags = "payments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment order retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Payment order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(id));
    }

    @Operation(
            summary = "Update payment order",
            description = "Updates the payment order for the given ID",
            tags = "payments",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment order to update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment order updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @PutMapping()
    public ResponseEntity<Void> updatePaymentOrder(@RequestParam(name = "id") String  id,
                                                   @RequestHeader(name = "X-User-Email") String email,
                                                   @RequestParam(name = "status") String status) {
        paymentService.updatePaymentStatus(id,email, status);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete payment order",
            description = "Deletes the payment order for the given ID",
            tags = "payments",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Payment order deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Payment order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentOrder(@PathVariable Long id) {
        paymentService.deletePaymentOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/webhook")
    @Operation(summary = "Handle Razorpay webhook", description = "Handles Razorpay webhook")
    public void handleWebhook(@RequestBody Map<String, Object> payload) {
        Map<String, Object> innerPayload = (Map<String, Object>) payload.get("payload");
        if (innerPayload != null) {
            Map<String, Object> payment = (Map<String, Object>) innerPayload.get("payment");
            if (payment != null) {
                Map<String, Object> entity = (Map<String, Object>) payment.get("entity");
                if (entity != null) {
                    String paymentId = (String) entity.get("id");
                    String status = (String) payload.get("event");
                    String email = (String) entity.get("email");

                    if ("payment.captured".equals(status)) {
                        paymentService.updatePaymentStatus(paymentId,email, "SUCCESS");
                    } else if ("payment.failed".equals(status)) {
                        paymentService.updatePaymentStatus(paymentId, email, "FAILED");
                    }
                }
            }
        }
    }
}