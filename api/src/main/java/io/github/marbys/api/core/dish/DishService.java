package io.github.marbys.api.core.dish;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DishService {

    @GetMapping("/dish/{dishId}")
    Mono<Dish> getDish(@PathVariable int dishId);

    @GetMapping("/dish")
    Flux<Dish> getMenu(@RequestParam(value = "restaurantId", required = true) int restaurantId);
    
}
