package io.github.marbys.microservices.order.services;

import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import reactor.core.publisher.Mono;

public class OrderServiceImpl implements OrderService {

    @Override
    public Mono<Order> getOrder(int orderId) {
        return null;
    }
}
