package io.github.marbys.microservices.order.persistence;

import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<RequestedDish, Integer> {
}
