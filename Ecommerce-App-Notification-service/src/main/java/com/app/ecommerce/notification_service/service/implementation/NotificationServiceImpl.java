package com.app.ecommerce.notification_service.service.implementation;

import com.app.ecommerce.notification_service.service.specification.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;

    @RabbitListener(queues = "order-status-update")
    public void receiveOrderUpdate(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> messageMap=null;
        try {
            messageMap = objectMapper.readValue(message, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("Received order status update(readValue): {}", messageMap);

        String email = (String) messageMap.get("email");
        String status = (String) messageMap.get("status");
        String orderId = (String) messageMap.get("orderId");

        emailService.sendEmail(
                email,
                "Order Status Update",
                String.format("Your order #%s status has been updated to: %s", orderId, status)
        );
    }

    @RabbitListener(queues = "payment-status-update")
    public void receivePaymentUpdate(String message) {
        log.info("Received payment status update: {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payment = null;
        try {
            payment = objectMapper.readValue(message, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("Received payment status update(readValue): {}", payment);

        String email = (String) payment.get("email");
        String paymentId = (String) payment.get("paymentId");
        String status = (String) payment.get("status");

        emailService.sendEmail(
                email,
                "Payment Status Update",
                String.format("Your payment #%s status is: %s", paymentId, status)
        );
    }

    @Override
    public void sendOrderConfirmationEmail(String email, String orderId) {
        String subject = "Order Confirmation";
        String body = "Your order #" + orderId + " has been confirmed. Thank you for shopping with us!";
        emailService.sendEmail(email, subject, body);
    }

    @Override
    public void sendPaymentSuccessEmail(String email, String orderId) {
        String subject = "Payment Successful";
        String body = "Payment for order #" + orderId + " was successful. Your order is being processed.";
        emailService.sendEmail(email, subject, body);
    }

    @Override
    public void sendPaymentFailureEmail(String email, String orderId) {
        String subject = "Payment Failed";
        String body = "Payment for order #" + orderId + " failed. Please try again.";
        emailService.sendEmail(email, subject, body);
    }
}
