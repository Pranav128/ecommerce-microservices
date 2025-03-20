package com.app.ecommerce.product_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		info = @Info(
				title = "Ecommerce Product Service API",
				description = "API for managing products in an ecommerce application",
				version = "1.0.0",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "Pranav Pisal",
						email = "pranavpisal128@gmail.com"
				)
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local development server")
		},
		tags = {
				@Tag(name = "products", description = "Product operations"),
				@Tag(name = "orders", description = "Order operations"),
				@Tag(name = "cart", description = "Cart operations")
		}
)
@SpringBootApplication
public class EcommerceAppProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppProductServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
