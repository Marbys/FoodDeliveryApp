package io.github.marbys.microservices.restaurant.persistence;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Integer> {
}
