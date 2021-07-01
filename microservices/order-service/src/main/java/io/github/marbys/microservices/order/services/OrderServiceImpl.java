package io.github.marbys.microservices.order.services;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.microservices.order.persistence.DishRepository;
import io.github.marbys.microservices.order.persistence.OrderEntity;
import io.github.marbys.microservices.order.persistence.OrderRepository;
import io.github.marbys.microservices.order.persistence.RequestedDish;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.just;

@RestController
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final DishRepository dishRepository;
    private final ServiceUtil serviceUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ServiceUtil serviceUtil, DishRepository dishRepository) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.dishRepository = dishRepository;
    }

    @Override
    public Mono<Order> getOrder(int orderId) {

        if (orderId < 1)
            throw new InvalidInputException("Invalid orderId: " + orderId);

        OrderEntity foundEntity = repository.findById(orderId).orElseThrow(() -> new NotFoundException("No order found for orderId: " + orderId));
        List<DishSummary> dishes = foundEntity.getRequestedDishes().stream().map(requestedDish -> new DishSummary(requestedDish.getId(), requestedDish.getName(), requestedDish.getDescription(), requestedDish.getPrice())).collect(Collectors.toList());
        Order order = new Order(foundEntity.getRestaurantId(), foundEntity.getOrderId(), dishes, foundEntity.getCustomerAddress(), foundEntity.getOrderCreatedAt(), null);


        return just(order);
    }

    @Override
    public Order placeOrder(Order order) {
//        List<RequestedDish> requestedDishes = order.getRequestedDishes().stream().map(dishSummary -> new RequestedDish(dishSummary.getName(), dishSummary.getDescription(), dishSummary.getPrice())).collect(Collectors.toList());
//        System.out.println(requestedDishes);
//        OrderEntity orderEntity = new OrderEntity(order.getRestaurantId(), order.getOrderId(), requestedDishes, order.getCustomerAddress(), LocalDateTime.now());
//        requestedDishes.get(0).setOrderEntity(orderEntity);
//        orderEntity.setRequestedDishes(requestedDishes);
//        System.out.println(requestedDishes);
//        OrderEntity savedEntity = repository.save(orderEntity);

        System.out.println(order.toString());

        OrderEntity orderEntity = new OrderEntity(order.getRestaurantId(), order.getOrderId(), null, order.getCustomerAddress(), LocalDateTime.now());

        System.out.println(orderEntity);
        List<RequestedDish> requestedDishes = order.getDishSummaries().stream().map(s -> new RequestedDish(orderEntity, s.getName(), s.getDescription(), s.getPrice())).collect(Collectors.toList());

        System.out.println(requestedDishes);
        orderEntity.setRequestedDishes(requestedDishes);

        repository.save(orderEntity);
        dishRepository.saveAll(requestedDishes);

        return order;
    }

//    @Override
//    public Flux<Order> getAllOrders(int restaurantId) {
//        return Flux.just(new Order(restaurantId, 1, Collections.singletonList(new Dish()), "Szeligowska", LocalDateTime.now(), "SA"));
//    }
}
