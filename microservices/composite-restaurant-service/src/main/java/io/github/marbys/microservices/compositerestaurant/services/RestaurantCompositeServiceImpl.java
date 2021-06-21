package io.github.marbys.microservices.compositerestaurant.services;

import io.github.marbys.api.core.composite.DishSummary;
import io.github.marbys.api.core.composite.RestaurantAggregate;
import io.github.marbys.api.core.composite.RestaurantCompositeService;
import io.github.marbys.api.core.composite.ServiceAddresses;
import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return  Mono.zip(
                values -> toRestaurantAggregate((Restaurant) values[0], (List<Dish>) values[1], serviceUtil.getServiceAddress()),
                integration.getRestaurant(restaurantId),
                integration.getMenu(restaurantId).collectList())
                .doOnError(ex -> LOG.warn("getCompositeRestaurant failed: {}", ex.toString()))
                .log();
    }

    private RestaurantAggregate toRestaurantAggregate(Restaurant restaurant,List<Dish> menu,  String serviceAddress) {
        List<DishSummary> list = menu.stream().map(dish -> new DishSummary(dish.getDishId(), dish.getName(), dish.getDescription(), dish.getPrice())).collect(Collectors.toList());
        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, menu.get(0).getServiceAddress(), null, restaurant.getServiceAddress());
        RestaurantAggregate restaurantAggregate = new RestaurantAggregate(restaurant.getRestaurantId(), restaurant.getName(), restaurant.getRating(), list, serviceAddresses);
        return restaurantAggregate;
    }
}
