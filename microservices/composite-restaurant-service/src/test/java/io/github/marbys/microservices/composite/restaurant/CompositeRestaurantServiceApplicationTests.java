package io.github.marbys.microservices.composite.restaurant;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.composite.RestaurantCompositeService;
import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.microservices.composite.restaurant.services.RestaurantCompositeIntegration;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CompositeRestaurantServiceApplicationTests {

    private static final int ID_OK = 1;
    private static final int ID_NOT_FOUND = 2;
    private static final int ID_INVALID = 3;

    @Autowired
    private WebTestClient client;

    @MockBean
    private RestaurantCompositeIntegration integration;

    @BeforeEach
    public void setUp() {
        when(integration.getRestaurant(ID_OK))
                .thenReturn(Mono.just(new Restaurant(ID_OK, "name", 5, "address")));

        when(integration.getDish(ID_OK))
                .thenReturn(Mono.just(new Dish(ID_OK, ID_OK, "name", "desc", 1, "address")));

        when(integration.getOrder(ID_OK))
                .thenReturn(Mono.just(new Order(ID_OK, ID_OK, Collections.singletonList(new DishSummary()), "customerAddress", "address")));

        when(integration.getRestaurant(ID_NOT_FOUND))
                .thenThrow(new NotFoundException("NOT FOUND: " + ID_NOT_FOUND));

        when(integration.getRestaurant(ID_INVALID))
                .thenThrow(new InvalidInputException("INVALID INPUT: " + ID_INVALID));

        when(integration.getMenu(ID_OK))
                .thenReturn(Flux.fromIterable(Collections.singletonList(new Dish(ID_OK, ID_OK, "name", "desc", 1, "address"))));

    }

    @Test
    public void getRestaurantById() {
        getAndVerifyRestaurant(ID_OK, HttpStatus.OK)
                .jsonPath("$.restaurantId").isEqualTo(ID_OK)
                .jsonPath("$.menu.length()").isEqualTo(1)
                .jsonPath("$.orders.length()").isEqualTo(1);
    }

    @Test
    public void getRestaurantNotFound() {
        getAndVerifyRestaurant(ID_NOT_FOUND, HttpStatus.NOT_FOUND)
                .jsonPath("$.path").isEqualTo("/composite-restaurant/" + ID_NOT_FOUND)
                .jsonPath("$.message").isEqualTo("NOT FOUND: " + ID_NOT_FOUND);
    }

    @Test
    public void getRestaurantInvalidInput() {
        getAndVerifyRestaurant(ID_INVALID, HttpStatus.UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/composite-restaurant/" + ID_INVALID)
                .jsonPath("$.message").isEqualTo("INVALID INPUT: " + ID_INVALID);
    }

    private WebTestClient.BodyContentSpec getAndVerifyRestaurant(int restaurantId, HttpStatus expectedStatus) {
        return client.get()
                .uri("/composite-restaurant/" + restaurantId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

}
