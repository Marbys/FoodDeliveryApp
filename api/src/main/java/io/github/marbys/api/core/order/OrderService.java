package io.github.marbys.api.core.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface OrderService {

    @GetMapping("/order/{orderId}")
    Mono<Order> getOrder(@PathVariable int orderId);
}
