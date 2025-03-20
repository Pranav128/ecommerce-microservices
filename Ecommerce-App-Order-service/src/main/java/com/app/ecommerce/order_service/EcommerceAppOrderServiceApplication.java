package com.app.ecommerce.order_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

@OpenAPIDefinition( info =
					@Info(
								title = "Ecommerce App Order Service",
								version = "1.0",
								description = "Ecommerce App Order Service",
								contact = @Contact(name = "Ecommerce App", email = "pranavpisal2528@gmail.com"),
								summary = "Ecommerce App Order Service"
					),
					servers = {
							@Server(url = "http://localhost:8082", description = "Ecommerce App Order Service")
					}
)
@SpringBootApplication
public class EcommerceAppOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppOrderServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Queue orderStatusUpdateQueue() {
		return new Queue("order-status-update");
	}
}
