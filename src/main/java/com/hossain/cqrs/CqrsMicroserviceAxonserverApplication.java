package com.hossain.cqrs;

import com.hossain.cqrs.command.api.exception.ProductServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CqrsMicroserviceAxonserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CqrsMicroserviceAxonserverApplication.class, args);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler(
				"products",
				configuration -> new ProductServiceEventsErrorHandler()
		);
	}
}
