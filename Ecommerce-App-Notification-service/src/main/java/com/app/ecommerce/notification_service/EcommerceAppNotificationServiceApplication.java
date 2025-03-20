package com.app.ecommerce.notification_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Ecommerce-App-Notification-service", version = "1.0",
		description = "Ecommerce-App-Notification-service",
		contact = @Contact(name = "Ecommerce-App-Notification-service", url = "https://github.com/pranav128"),
		license = @License(name = "Ecommerce-App-Notification-service", url = "https://github.com/pranav128")),
		servers = {
				@Server(url = "https://github.com/pranav128", description = "Ecommerce-App-Notification-service")
		}
)
@SpringBootApplication
public class EcommerceAppNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppNotificationServiceApplication.class, args);
	}
}
