package io.github.marbys.microservices.restaurant;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.event.Event;
import io.github.marbys.microservices.restaurant.persistence.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static io.github.marbys.api.event.Event.Type.CREATE;
import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureWebTestClient
class RestaurantServiceApplicationTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private RestaurantRepository repository;

    private AbstractMessageChannel input;

    @Autowired
    private Sink channels;

    @BeforeEach
    public void setUpDb() {
        input = (AbstractMessageChannel) channels.input();
        repository.deleteAll();
    }

    @Test
    public void getRestaurantByRestaurantId() {
        int restaurantId = 1;
        assertEquals(0, repository.count());

        sendCreateRestaurantEvent(restaurantId);

        assertEquals(1, repository.count());

        getAndVerifyRestaurant(restaurantId, HttpStatus.OK)
                .jsonPath("$.restaurantId").isEqualTo(restaurantId);

    }

    @Test
    public void getRestaurantInvalidParameter() {
        getAndVerifyRestaurant("/no-integer", HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @Test
    public void getRestaurantNegativeRestaurantId() {
        int restaurantId = -1;
        getAndVerifyRestaurant(restaurantId, HttpStatus.UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/restaurant/" + restaurantId)
                .jsonPath("$.message").isEqualTo("Invalid restaurantId: " + restaurantId);
    }

    @Test
    public void getRestaurantNotFound() {
        int restaurantId = 999;
        getAndVerifyRestaurant(restaurantId, HttpStatus.NOT_FOUND)
                .jsonPath("$.path").isEqualTo("/restaurant/" + restaurantId)
                .jsonPath("$.message").isEqualTo("No restaurant found for restaurantId: " + 999);
    }


    private void sendCreateRestaurantEvent(int restaurantId) {
        DishSummary dishSummary = new DishSummary(restaurantId, restaurantId, "name", "desc", 1.0);
        List<DishSummary> list = new ArrayList<>();
        list.add(dishSummary);
        Restaurant Restaurant = new Restaurant(restaurantId, "name", restaurantId, "sa");

        Event<Integer, Restaurant> event = new Event(CREATE, restaurantId, Restaurant);
        input.send(new GenericMessage<>(event));
    }

    private WebTestClient.BodyContentSpec getAndVerifyRestaurant(String restaurantIdPath, HttpStatus expectedStatus) {
        return client.get()
                .uri("/restaurant" + restaurantIdPath)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAndVerifyRestaurant(int restaurantId, HttpStatus expectedStatus) {
        return getAndVerifyRestaurant("/" + restaurantId, expectedStatus);
    }

}
