package io.github.marbys.microservices.order.services;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.microservices.order.persistence.OrderEntity;
import io.github.marbys.microservices.order.persistence.OrderRepository;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.just;

@RestController
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ServiceUtil serviceUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ServiceUtil serviceUtil, RestTemplate restTemplate) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Order> getOrder(int orderId) {

        if (orderId < 1)
            throw new InvalidInputException("Invalid orderId: " + orderId);

        OrderEntity foundEntity = repository.findById(orderId).orElseThrow(() -> new NotFoundException("No order found for orderId: " + orderId));

        List<Dish> dishes = foundEntity.getRequestedDishes().stream().map(requestedDish -> {
            Dish foundDish = restTemplate.getForObject("http://localhost:7002/dish/" + requestedDish.getDishId(), Dish.class);
            return foundDish;
        }).collect(Collectors.toList());

        Order order = new Order(foundEntity.getRestaurantId(), foundEntity.getOrderId(), dishes, foundEntity.getCustomerAddress(), foundEntity.getOrderCreatedAt(), null);


        return just(order);
    }

    @Override
    public Order placeOrder(Order order) {
        return null;
    }

//    @Override
//    public Flux<Order> getAllOrders(int restaurantId) {
//        return Flux.just(new Order(restaurantId, 1, Collections.singletonList(new Dish()), "Szeligowska", LocalDateTime.now(), "SA"));
//    }
}
