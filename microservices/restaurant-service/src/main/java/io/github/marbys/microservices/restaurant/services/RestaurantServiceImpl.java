package io.github.marbys.microservices.restaurant.services;

import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.core.restaurant.RestaurantService;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@RestController
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public Mono<Restaurant> getRestaurant(int restaurantId) {
        return just(new Restaurant(restaurantId, "name", 5, null));
    }
}
