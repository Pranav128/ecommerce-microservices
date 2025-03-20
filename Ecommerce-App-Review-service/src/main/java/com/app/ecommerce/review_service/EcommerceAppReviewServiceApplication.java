package com.app.ecommerce.review_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Ecommerce Review Service API",
				description = "API for managing reviews in an ecommerce application",
				version = "1.0.0",
				contact = @Contact(name = "Pranav Pisal", email = "pranavpisal128@gmail.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
		),
		servers = {
				@Server(url = "http://localhost:8088", description = "Local development server")
		}
)
@SpringBootApplication
public class EcommerceAppReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppReviewServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
