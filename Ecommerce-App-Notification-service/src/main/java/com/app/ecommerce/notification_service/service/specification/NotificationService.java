package com.app.ecommerce.notification_service.service.specification;

public interface NotificationService {
    void sendOrderConfirmationEmail(String email, String orderId);
    void sendPaymentSuccessEmail(String email, String orderId);
    void sendPaymentFailureEmail(String email, String orderId);
}
