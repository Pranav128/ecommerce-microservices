package com.app.ecommerce.review_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/message")
@Tag(name = "Message", description = "Message API")
public class MessageController {

	@Value("${spring.boot.message}")
	private String message;

	@GetMapping
	@Operation(summary = "Get message", description = "Returns the message and refreshes the properties")
	public String getMessage() {
		return message;
	}
}
