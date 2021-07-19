package io.github.marbys.microservices.restaurant.persistence;

import io.github.marbys.api.core.restaurant.Restaurant;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Integer> {

    Optional<RestaurantEntity> findByRestaurantId(int restaurantId);
}
