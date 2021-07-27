package io.github.marbys.api.core.restaurant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantService {

    @GetMapping("restaurant/{restaurantId}")
    Mono<Restaurant> getRestaurant(@PathVariable int restaurantId);

    @GetMapping("/restaurants")
    Flux<Restaurant> getAllRestaurants();

    @PostMapping("/restaurant")
    Restaurant createRestaurant(@RequestBody Restaurant restaurant);
}
