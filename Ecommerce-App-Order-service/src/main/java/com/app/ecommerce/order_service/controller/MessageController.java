package com.app.ecommerce.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Tag(name = "Message Controller", description = "Message Controller for Product Service to refresh properties")
public class MessageController {
    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("/message")
    @Operation(summary = "Get message", description = "Returns the message and refreshes the properties")
    public String getMessage() {
        return message;
    }
}