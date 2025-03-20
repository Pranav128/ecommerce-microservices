package com.app.ecommerce.user_service;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ecommerce-App-User-service",
								version = "1.0",
								description = "Ecommerce-App-User-service for authentication and authorization and other user related operations",
								termsOfService = "https://ecommerce28.netlify.app/",
								contact = @Contact(name = "Ecommerce-App-User-service", email = "pranavpisal@gmail.com"),
								license = @License(name = "Ecommerce-App-User-service", url = "https://ecommerce28.netlify.app/")),
					servers = @Server(url = "/", description = "Default SerOver"),
					externalDocs = @ExternalDocumentation(url = "https://ecommerce28.netlify.app/")
)

public class EcommerceAppUserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppUserServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
