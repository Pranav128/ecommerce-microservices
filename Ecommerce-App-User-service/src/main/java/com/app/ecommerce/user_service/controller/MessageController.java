package com.app.ecommerce.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Tag(name = "Message", description = "Message Controller")
public class MessageController {
    @Value("${spring.boot.message}")
    private String message;

    @Operation(summary = "Get message", description = "Returns the message and refreshes the properties")
    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}
