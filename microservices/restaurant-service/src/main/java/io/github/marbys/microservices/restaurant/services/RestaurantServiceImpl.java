package io.github.marbys.microservices.restaurant.services;

import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.api.core.restaurant.RestaurantService;
import io.github.marbys.microservices.restaurant.persistence.RestaurantEntity;
import io.github.marbys.microservices.restaurant.persistence.RestaurantRepository;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@RestController
public class RestaurantServiceImpl implements RestaurantService {
    
    private final RestaurantRepository repository;
    private final ServiceUtil serviceUtil;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Mono<Restaurant> getRestaurant(int restaurantId) {
        if(restaurantId < 1)
            throw new InvalidInputException("Invalid restaurantId: " + restaurantId);

        RestaurantEntity foundEntity = repository.findById(restaurantId).orElseThrow(() -> new NotFoundException("No restaurant found for restaurantId: " + restaurantId));
        Restaurant restaurant = new Restaurant(foundEntity.getRestaurantId(), foundEntity.getName(), foundEntity.getRating(), null);
        return just(restaurant);
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        RestaurantEntity newEntity =  new RestaurantEntity(restaurant.getRestaurantId(), restaurant.getName(), restaurant.getRating());
        RestaurantEntity savedEntity = repository.save(newEntity);
        return new Restaurant(savedEntity.getRestaurantId(), savedEntity.getName(), savedEntity.getRating(),null);
    }
}
