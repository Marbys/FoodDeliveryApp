package io.github.marbys.microservices.dish.persistence;

import io.github.marbys.api.core.dish.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends CrudRepository<DishEntity, Integer> {

    @Transactional(readOnly = true)
    List<DishEntity> findByRestaurantId(int restaurantId);

    Optional<DishEntity> findByDishId(int dishId);
}
