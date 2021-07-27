package io.github.marbys.microservices.composite.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.github.marbys")
public class CompositeRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompositeRestaurantServiceApplication.class, args);
	}

}
