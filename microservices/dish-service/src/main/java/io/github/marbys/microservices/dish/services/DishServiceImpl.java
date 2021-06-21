package io.github.marbys.microservices.dish.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.dish.DishService;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@RestController
public class DishServiceImpl implements DishService {

    @Override
    public Mono<Dish> getDish(int dishId) {
        return just(new Dish(1,"XD", "desc", 1, null));
    }

    @Override
    public Flux<Dish> getMenu(int restaurantId) {
        return Flux.just(new Dish(1,"XD", "desc", 1, null));
    }
}
