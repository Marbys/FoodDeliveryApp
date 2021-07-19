package io.github.marbys.microservices.order.services;

import com.mongodb.DuplicateKeyException;
import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.microservices.order.persistence.OrderEntity;
import io.github.marbys.microservices.order.persistence.OrderRepository;
import io.github.marbys.microservices.order.persistence.RequestedDish;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final SequenceGeneratorService generatorService;
    private final OrderRepository repository;
    private final ServiceUtil serviceUtil;
    private final OrderMapper mapper;

    @Autowired
    public OrderServiceImpl(SequenceGeneratorService generatorService, OrderRepository repository, ServiceUtil serviceUtil, OrderMapper mapper) {
        this.generatorService = generatorService;
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
    }

    @Override
    public Mono<Order> getOrder(int orderId) {

        if (orderId < 1)
            throw new InvalidInputException("Invalid orderId: " + orderId);

        Mono<OrderEntity> foundEntity = repository.findByOrderId(orderId);
        Mono<Order> foundOrder = foundEntity.log().map(e -> {
            List<DishSummary> dishes = e.getRequestedDishes().stream().map(d -> new DishSummary(e.getRestaurantId(), d.getDishId(), d.getName(), d.getDescription(), d.getPrice())).collect(Collectors.toList());
            return new Order(e.getRestaurantId(), e.getOrderId(), dishes, e.getCustomerAddress(), e.getOrderCreatedAt(), serviceUtil.getServiceAddress());
        });

        return foundOrder;
    }

    @Override
    public Order placeOrder(Order order) {

        LOG.debug("Attempting to save order with id: " + order.getOrderId());

        //List<RequestedDish> requestedDishes = order.getDishSummaries().stream().map(s -> new RequestedDish(s.getName(), s.getDescription(), s.getPrice())).collect(Collectors.toList());
        List<RequestedDish> requestedDishes = mapper.dishSummaryListToRequestedDishList(order.getDishSummaries());
        OrderEntity orderEntity = new OrderEntity(order.getRestaurantId(), (int) generatorService.generateSequence(OrderEntity.SEQUENCE_NAME), requestedDishes, null);
        repository.save(orderEntity)
        .log()
        .onErrorMap(
                DuplicateKeyException.class,
                ex -> new InvalidInputException("Duplicate key, Order Id: " + order.getOrderId())
        ).subscribe();

        LOG.debug("Order saved!");
        return order;
    }

//    @Override
//    public Flux<Order> getAllOrders(int restaurantId) {
//        return Flux.just(new Order(restaurantId, 1, Collections.singletonList(new Dish()), "Szeligowska", LocalDateTime.now(), "SA"));
//    }
}
