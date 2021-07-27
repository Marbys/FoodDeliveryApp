package io.github.marbys.api.core.order;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    @GetMapping("/order/{orderId}")
    Mono<Order> getOrder(@PathVariable int orderId);

//    @GetMapping("/order")
//    Flux<Order> getAllOrders(@RequestParam(value = "restaurantId", required = true) int restaurantId);

    @PostMapping("/order")
    Order placeOrder(@RequestBody Order order);
}
