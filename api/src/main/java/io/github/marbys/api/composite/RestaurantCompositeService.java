package io.github.marbys.api.composite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface RestaurantCompositeService {

    @GetMapping("/composite-restaurant/{restaurantId}")
    Mono<RestaurantAggregate> getCompositeRestaurant(@PathVariable int restaurantId);

}
