package io.github.marbys.api.composite;

import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.restaurant.Restaurant;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantCompositeService {

    @GetMapping("/composite-restaurant/{restaurantId}")
    Mono<RestaurantAggregate> getCompositeRestaurant(@PathVariable int restaurantId);

    @GetMapping("/restaurants")
    Flux<Restaurant> getAllRestaurants();

    @PostMapping("/composite-restaurant")
    RestaurantAggregate createCompositeRestaurant(@RequestBody RestaurantAggregate restaurantAggregate);

    @PostMapping("/order")
    Order placeOrder(@RequestBody Order order);

}
