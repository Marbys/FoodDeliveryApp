package io.github.marbys.api.core.restaurant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface RestaurantService {

    @GetMapping("restaurant/{restaurantId}")
    Mono<Restaurant> getRestaurant(@PathVariable int restaurantId);
}
