package com.app.ecommerce.payment_service.service.implementation;

import com.app.ecommerce.payment_service.dto.response.PaymentResponse;
import com.app.ecommerce.payment_service.exception.PaymentException;
import com.app.ecommerce.payment_service.model.Payment;
import com.app.ecommerce.payment_service.repository.PaymentRepository;
import com.app.ecommerce.payment_service.service.specification.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.currency}")
    private String currency;
    
    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public PaymentResponse createPaymentOrder(Long orderId, Double amount, String paymentMethod) throws RazorpayException {

        Order order = null;
        if(paymentMethod.equals("razorpay")) {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Amount in paise (100 paise = 1 INR)
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "Receipt_" + orderId);

            // Create order in Razorpay
            order = razorpayClient.orders.create(orderRequest);
            System.out.println(order.toString());
        }

        // Save payment record in DB
        Payment payment = Payment.builder()
                .orderId(orderId)
                .paymentId(order==null? "cod_payment" :order.get("id").toString())
                .amount(amount)
                .paymentMethod(paymentMethod.toUpperCase())
                .status("PENDING")
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentResponse.class);
//        return PaymentResponse.builder()
//                .orderId(orderId)
//                .paymentId(order.get("id"))
//                .status("PENDING")
//                .amount(amount)
//                .build();
    }

    @Override
    public void updatePaymentStatus(String paymentId, String email, String status) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentException("Payment not found"));

        Map<String, Object> message = new HashMap<>();
        message.put("email", email);
        message.put("paymentId", payment.getPaymentId());
        message.put("status", status);
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert payment update to JSON", e);
        }

        log.info("Sending payment status update(map): {}", message +" : "+ getClass().getSimpleName() );
        log.info("Sending payment status update(ObjectMapper): {}", messageString +" : "+ getClass().getSimpleName() );

        // Send payment status update to RabbitMQ exchange
        rabbitTemplate.convertAndSend("payment-status-update", messageString);

        // Update payment status in DB
        payment.setStatus(status);
        payment.setPaymentDate(LocalDateTime.now());

        // Save payment record in DB
        paymentRepository.save(payment);
    }

    @Override
    public void deletePaymentOrder(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentException("Payment not found"));
        paymentRepository.delete(payment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentException("Payment not found"));
        return modelMapper.map(payment, PaymentResponse.class);
    }
}
