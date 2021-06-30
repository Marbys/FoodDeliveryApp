package io.github.marbys.microservices.dish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("io.github.marbys")
public class DishServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishServiceApplication.class, args);
	}


}
