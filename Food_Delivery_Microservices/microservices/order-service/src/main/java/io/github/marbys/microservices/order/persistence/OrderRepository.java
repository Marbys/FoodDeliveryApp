package io.github.marbys.microservices.order.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, String> {

    Flux<OrderEntity> findByRestaurantId(int restaurantId);
    Mono<OrderEntity> findByOrderId(int orderId);
}
