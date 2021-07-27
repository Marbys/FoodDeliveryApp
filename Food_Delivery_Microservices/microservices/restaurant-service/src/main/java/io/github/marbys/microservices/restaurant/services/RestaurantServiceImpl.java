package io.github.marbys.microservices.restaurant.services;

import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.core.restaurant.RestaurantService;
import io.github.marbys.microservices.restaurant.persistence.RestaurantEntity;
import io.github.marbys.microservices.restaurant.persistence.RestaurantRepository;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.aspectj.weaver.ast.Not;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.function.Supplier;
import java.util.stream.Collector;

import static reactor.core.publisher.Mono.just;

@RestController
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;
    private final ServiceUtil serviceUtil;
    private final RestaurantMapper mapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, ServiceUtil serviceUtil, RestaurantMapper mapper) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
    }

    @Override
    public Mono<Restaurant> getRestaurant(int restaurantId) {
        if (restaurantId < 1)
            throw new InvalidInputException("Invalid restaurantId: " + restaurantId);

        RestaurantEntity foundEntity = repository.findByRestaurantId(restaurantId).orElseThrow(() -> new NotFoundException("No restaurant found for restaurantId: " + restaurantId));
        Restaurant foundApi = mapper.entityToApi(foundEntity);
        foundApi.setServiceAddress(serviceUtil.getServiceAddress());

        return Mono.just(foundApi);
    }

    @Override
    public Flux<Restaurant> getAllRestaurants() {
        Flux<Restaurant> restaurant = Flux.fromIterable(repository.findAll()).map(e -> mapper.entityToApi(e)).log();
        return restaurant;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        RestaurantEntity newEntity = mapper.apiToEntity(restaurant);
        RestaurantEntity savedEntity = repository.save(newEntity);

        return mapper.entityToApi(savedEntity);
    }
}
