package com.app.ecommerce.cart_service;

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
				title = "Ecommerce Cart Service API",
				description = "API for managing carts in an ecommerce application",
				version = "1.0.0"
		),
		servers = {
				@Server(url = "http://localhost:8081", description = "Local development server")
		},
		tags = {
				@Tag(name = "cart", description = "Cart operations"),
				@Tag(name = "cart-items", description = "Cart item operations")
		}
)
@SpringBootApplication
public class EcommerceAppCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppCartServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
