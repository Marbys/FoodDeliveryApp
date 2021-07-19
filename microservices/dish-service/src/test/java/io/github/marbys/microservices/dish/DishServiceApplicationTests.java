package io.github.marbys.microservices.dish;

import io.github.marbys.microservices.dish.services.DishServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureWebTestClient
class DishServiceApplicationTests {



}
