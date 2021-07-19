package io.github.marbys.microservices.order;


import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.event.Event;
import io.github.marbys.microservices.order.persistence.OrderRepository;
import io.github.marbys.util.exceptions.InvalidInputException;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
public class OrderServiceApplicationTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private Sink channels;

    private AbstractMessageChannel input = null;

    @BeforeEach
    public void setUpDb() {
        input = (AbstractMessageChannel) channels.input();
        repository.deleteAll().block();
    }

    @Test
    public void getOrderByOrderId() {
        int orderId = 1;

        assertEquals(0, (long) repository.count().block());
        sendCreateOrderEvent(orderId);
        assertEquals(1, (long) repository.count().block());

        getAndVerifyOrder(orderId, HttpStatus.OK)
                .jsonPath("$.orderId").isEqualTo(orderId);
    }

    @Test
    public void getOrderInvalidParameter() {
        getAndVerifyOrder("/no-integer", HttpStatus.BAD_REQUEST)
        .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @Test
    public void getOrderNotFound() {
        getAndVerifyOrder(999, HttpStatus.NOT_FOUND)
        .jsonPath("$.path").isEqualTo("/order/999")
        .jsonPath("$.message").isEqualTo("No orders found for orderId: " + 999);
    }

    @Test
    public void getOrderNegativeOrderId() {
        int orderId = -1;
        getAndVerifyOrder(orderId, HttpStatus.UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/order/" + orderId)
                .jsonPath("$.message").isEqualTo("Invalid orderId: " + orderId);
    }

    private void sendCreateOrderEvent(int orderId) {
        DishSummary dishSummary = new DishSummary(orderId, orderId, "name", "desc", 1.0);
        List<DishSummary> list = new ArrayList<>();
        list.add(dishSummary);
        Order order = new Order(orderId, orderId, null, "address", "sa");

        Event<Integer, Order> event = new Event<>(CREATE, orderId, order);
        input.send(new GenericMessage<>(event));
    }

    private WebTestClient.BodyContentSpec getAndVerifyOrder(String orderIdPath, HttpStatus expectedStatus) {
        return client.get()
                .uri("/order" + orderIdPath)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAndVerifyOrder(int orderId, HttpStatus expectedStatus) {
        return getAndVerifyOrder("/" + orderId, expectedStatus);
    }

}
