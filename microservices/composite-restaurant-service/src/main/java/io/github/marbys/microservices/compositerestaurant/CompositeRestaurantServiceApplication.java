package io.github.marbys.microservices.compositerestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@ComponentScan("io.github.marbys")
public class CompositeRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompositeRestaurantServiceApplication.class, args);
	}

}
