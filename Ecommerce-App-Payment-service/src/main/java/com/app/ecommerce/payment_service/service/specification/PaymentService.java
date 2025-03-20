package com.app.ecommerce.payment_service.service.specification;

import com.app.ecommerce.payment_service.dto.response.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {
    PaymentResponse createPaymentOrder(Long orderId, Double amount,String paymentMethod) throws RazorpayException;
    void updatePaymentStatus(String paymentId, String email, String status);
    void deletePaymentOrder(Long orderId);
    PaymentResponse getPaymentByOrderId(Long orderId);
}