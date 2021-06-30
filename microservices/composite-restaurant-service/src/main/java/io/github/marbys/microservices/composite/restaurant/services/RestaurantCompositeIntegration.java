package io.github.marbys.microservices.composite.restaurant.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.dish.DishService;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.core.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RestaurantCompositeIntegration implements DishService, RestaurantService, OrderService {

    private final WebClient webClient;

    private final String dishServiceUrl;
    private final String restaurantServiceUrl;
    private final String orderServiceUrl;

    @Autowired
    public RestaurantCompositeIntegration(
            WebClient.Builder webClient,
            @Value("${app.dish-service.host}") String dishServiceHost,
            @Value("${app.dish-service.port}") String dishServicePort,

            @Value("${app.restaurant-service.host}") String restaurantServiceHost,
            @Value("${app.restaurant-service.port}") String restaurantServicePort,

            @Value("${app.order-service.host}") String orderServiceHost,
            @Value("${app.order-service.port}") String orderServicePort
            ) {
        this.webClient = webClient.build();

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
        return webClient.post()
                .uri(dishServiceUrl + "/dish")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Dish.class)
                .block();
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
    public Restaurant createRestaurant(Restaurant restaurant) {
        return webClient.post()
                .uri(restaurantServiceUrl + "/restaurant")
                .body(restaurant, Restaurant.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Restaurant.class)
                .block();
    }

    @Override
    public Mono<Order> getOrder(int orderId) {
        return webClient.get()
                .uri(orderServiceUrl + "/order/" + orderId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Order.class);
    }

    @Override
    public Order placeOrder(Order order) {
        return null;
    }

//    @Override
//    public Flux<Order> getAllOrders(int restaurantId) {
//        return webClient.get()
//                .uri(orderServiceUrl + "/order?restaurantId=" + restaurantId)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(Order.class);
//    }
}
