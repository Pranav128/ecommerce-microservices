package com.app.ecommerce.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EcommerceAppConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppConfigServerApplication.class, args);
	}
}
