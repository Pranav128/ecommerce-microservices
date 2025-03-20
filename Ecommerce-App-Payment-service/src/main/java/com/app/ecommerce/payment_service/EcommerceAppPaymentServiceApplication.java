package com.app.ecommerce.payment_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(info = @Info(title = "Ecommerce-App-Payment-service", version = "1.0",
		description = "Ecommerce-App-Payment-service", contact = @Contact(name = "Ecommerce-App-Payment-service", email = "pranavpisal2528@.com")
))

@SpringBootApplication
public class EcommerceAppPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppPaymentServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public Queue queue() {
	    return new Queue("payment-status-update", false);
	}
}
