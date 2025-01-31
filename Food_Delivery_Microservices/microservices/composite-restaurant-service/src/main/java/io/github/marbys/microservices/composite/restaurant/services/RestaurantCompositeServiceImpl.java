package io.github.marbys.microservices.composite.restaurant.services;

import io.github.marbys.api.composite.*;
import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RestaurantCompositeServiceImpl implements RestaurantCompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantCompositeServiceImpl.class);

    private final RestaurantCompositeIntegration integration;
    private final ServiceUtil serviceUtil;


    @Autowired
    public RestaurantCompositeServiceImpl(RestaurantCompositeIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Mono<RestaurantAggregate> getCompositeRestaurant(int restaurantId) {
        return Mono.zip(
                values -> toRestaurantAggregate((Restaurant) values[0], (List<Dish>) values[1], (List<Order>) values[2], serviceUtil.getServiceAddress()),
                integration.getRestaurant(restaurantId),
                integration.getMenu(restaurantId).collectList(),
                Flux.from(integration.getOrder(restaurantId)).collectList())
                .doOnError(ex -> LOG.warn("getCompositeRestaurant failed: {}", ex.toString()))
                .log();
    }

    @Override
    public Flux<Restaurant> getAllRestaurants() {
        return integration.getAllRestaurants();
    }


    @Override
    public RestaurantAggregate createCompositeRestaurant(RestaurantAggregate restaurantAggregate) {
        Restaurant restaurant = new Restaurant(restaurantAggregate.getRestaurantId(), restaurantAggregate.getName(), restaurantAggregate.getRating(), null);
        integration.createRestaurant(restaurant);

        List<DishSummary> dishSummaryList = restaurantAggregate.getMenu();
        List<Dish> dishes = dishSummaryList.stream().map(s -> new Dish(restaurant.getRestaurantId(), s.getDishId(), s.getName(), s.getDescription(), s.getPrice(), null)).collect(Collectors.toList());
        dishes.forEach(integration::createDish);

        List<OrderSummary> orderSummaries = restaurantAggregate.getOrders();

        return restaurantAggregate;
    }

    @Override
    public Order placeOrder(Order order) {
        LOG.debug("Received order: " + order.toString());
        return integration.placeOrder(order);
    }

    private RestaurantAggregate toRestaurantAggregate(Restaurant restaurant, List<Dish> menu, List<Order> orders, String serviceAddress) {

        List<DishSummary> list = menu.stream().map(dish -> new DishSummary(dish.getRestaurantId(), dish.getDishId(), dish.getName(), dish.getDescription(), dish.getPrice())).collect(Collectors.toList());

        List<OrderSummary> orderSummaries = orders.stream().map(order -> {
            List<DishSummary> dishes = order
                    .getDishSummaries()
                    .stream()
                    .map(dishSummary -> new DishSummary(restaurant.getRestaurantId(), dishSummary.getDishId(), dishSummary.getName(), dishSummary.getDescription(), dishSummary.getPrice()))
                    .collect(Collectors.toList());
            return new OrderSummary(order.getOrderId(), dishes, order.getCustomerAddress(), order.getOrderCreatedAt());
        }).collect(Collectors.toList());

        String orderAddress = orders.isEmpty() ?  null : orders.get(0).getServiceAddress();
        String dishAddress = menu.isEmpty() ?  null : menu.get(0).getServiceAddress();

        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress,dishAddress, orderAddress , restaurant.getServiceAddress());
        RestaurantAggregate restaurantAggregate = new RestaurantAggregate(restaurant.getRestaurantId(), restaurant.getName(), restaurant.getRating(), list, orderSummaries, serviceAddresses);
        return restaurantAggregate;
    }
}
