package io.github.marbys.microservices.dish;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.event.Event;
import io.github.marbys.microservices.dish.persistence.DishRepository;
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

import static io.github.marbys.api.event.Event.Type.CREATE;
import static org.junit.Assert.assertEquals;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureWebTestClient
class DishServiceApplicationTests {

    @Autowired
    private DishRepository repository;

    @Autowired
    private WebTestClient client;

    @Autowired
    private Sink channels;

    private AbstractMessageChannel input;

    @BeforeEach
    public void setUpDb() {
        input = (AbstractMessageChannel)  channels.input();
        repository.deleteAll();
    }

    @Test
    public void getDishByDishId() {
        int dishId = 1;

        assertEquals(0, repository.count());

        sendCreateDishEvent(dishId);

        assertEquals(1, repository.count());

        getAndVerifyDish(dishId, HttpStatus.OK)
                .jsonPath("$.dishId").isEqualTo(dishId);
    }

    @Test
    public void getDishInvalidParameter() {
        getAndVerifyDish("/no-integer", HttpStatus.BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @Test
    public void getDishNegativeDishId() {
        int dishId = -1;
        getAndVerifyDish(dishId, HttpStatus.UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/dish/" + dishId)
                .jsonPath("$.message").isEqualTo("Invalid dishId: " + dishId);
    }

    private void sendCreateDishEvent(int dishId) {
        Dish dish = new Dish(dishId, dishId, "name", "desc", dishId, "sa");

        Event<Integer, Dish> event = new Event(CREATE, dishId, dish);
        input.send(new GenericMessage<>(event));
    }

    private WebTestClient.BodyContentSpec getAndVerifyDish(String dishIdPath, HttpStatus expectedStatus) {
        return client.get()
                .uri("/dish" + dishIdPath)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAndVerifyDish(int dishId, HttpStatus expectedStatus) {
        return getAndVerifyDish("/" + dishId, expectedStatus);
    }

}
