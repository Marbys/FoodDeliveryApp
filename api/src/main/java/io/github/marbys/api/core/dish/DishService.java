package io.github.marbys.api.core.dish;


import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DishService {

    @GetMapping(
            value = "/dish/{dishId}",
            produces = "application/json")
    Mono<Dish> getDish(@PathVariable int dishId);

    @GetMapping(
            value = "/dish",
            produces = "application/json")
    Flux<Dish> getMenu(@RequestParam(value = "restaurantId", required = true) int restaurantId);

    @PostMapping(
            value = "/dish",
            produces = "application/json",
            consumes = "application/json")
    Dish createDish(@RequestBody Dish dish);
}
