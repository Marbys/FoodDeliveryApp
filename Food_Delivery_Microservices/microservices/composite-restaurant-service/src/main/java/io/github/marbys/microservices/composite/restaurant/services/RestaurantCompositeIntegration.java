package io.github.marbys.microservices.composite.restaurant.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.dish.DishService;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.core.restaurant.RestaurantService;
import io.github.marbys.api.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableBinding(RestaurantCompositeIntegration.MessageSources.class)
public class RestaurantCompositeIntegration implements DishService, RestaurantService, OrderService {
    private final static Logger LOG = LoggerFactory.getLogger(RestaurantCompositeIntegration.class);

    private final WebClient webClient;

    private final String dishServiceUrl;
    private final String restaurantServiceUrl;
    private final String orderServiceUrl;
    private final MessageSources messageSources;

    @Autowired
    public RestaurantCompositeIntegration(
            WebClient.Builder webClient,
            MessageSources messageSources,
            @Value("${app.dish-service.host}") String dishServiceHost,
            @Value("${app.dish-service.port}") String dishServicePort,

            @Value("${app.restaurant-service.host}") String restaurantServiceHost,
            @Value("${app.restaurant-service.port}") String restaurantServicePort,

            @Value("${app.order-service.host}") String orderServiceHost,
            @Value("${app.order-service.port}") String orderServicePort
            ) {
        this.webClient = webClient.build();
        this.messageSources = messageSources;

        this.dishServiceUrl = "http://" + dishServiceHost + ":" + dishServicePort;
        this.restaurantServiceUrl = "http://" + restaurantServiceHost + ":" + restaurantServicePort;
        this.orderServiceUrl = "http://" + orderServiceHost + ":" + orderServicePort;
    }

    @Override
    public Mono<Dish> getDish(int dishId) {
        return webClient.get()
                .uri(dishServiceUrl + "/dish/" + dishId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Dish.class);
    }

    @Override
    public Flux<Dish> getMenu(int restaurantId) {
        return webClient.get()
                .uri(dishServiceUrl + "/dish?restaurantId=" + restaurantId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Dish.class);
    }

    @Override
    public Dish createDish(Dish dish) {
        messageSources.outputDishes().send(MessageBuilder.withPayload(new Event(Event.Type.CREATE, dish.getDishId(), dish)).build());
        return dish;
    }


    @Override
    public Mono<Restaurant> getRestaurant(int restaurantId) {
        return webClient.get()
                .uri(restaurantServiceUrl + "/restaurant/" + restaurantId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Restaurant.class);
    }

    @Override
    public Flux<Restaurant> getAllRestaurants() {
        return webClient.get()
                .uri(restaurantServiceUrl + "/restaurants")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Restaurant.class);
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        messageSources.outputRestaurants().send(MessageBuilder.withPayload(new Event(Event.Type.CREATE, restaurant.getRestaurantId(), restaurant)).build());
        return restaurant;
    }

    @Override
    public Mono<Order> getOrder(int orderId) {
        return webClient.get()
                .uri(orderServiceUrl + "/order/" + orderId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Order.class);
    }

    //todo czy aby dobre id
    @Override
    public Order placeOrder(Order order) {
        LOG.debug("Received order: " + order.toString());
        order.setOrderCreatedAt(LocalDateTime.now());
        messageSources.outputOrders().send(MessageBuilder.withPayload(new Event(Event.Type.CREATE, order.getOrderId(), order)).build());
        return order;
    }


    public interface MessageSources {
        String OUTPUT_DISHES = "output-dishes";
        String OUTPUT_ORDERS = "output-orders";
        String OUTPUT_RESTAURANTS = "output-restaurants";

        @Output(OUTPUT_DISHES)
        MessageChannel outputDishes();

        @Output(OUTPUT_ORDERS)
        MessageChannel outputOrders();

        @Output(OUTPUT_RESTAURANTS)
        MessageChannel outputRestaurants();
    }
}
