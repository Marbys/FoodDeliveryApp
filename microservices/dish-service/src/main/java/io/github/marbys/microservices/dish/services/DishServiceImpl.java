package io.github.marbys.microservices.dish.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.dish.DishService;
import io.github.marbys.microservices.dish.persistence.DishEntity;
import io.github.marbys.microservices.dish.persistence.DishRepository;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.github.marbys.util.exceptions.NotFoundException;
import io.github.marbys.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.just;

@RestController
public class DishServiceImpl implements DishService {

    private static final Logger LOG = LoggerFactory.getLogger(DishServiceImpl.class);

    private final DishRepository repository;
    private final ServiceUtil serviceUtil;

    @Autowired
    public DishServiceImpl(DishRepository repository, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Mono<Dish> getDish(int dishId) {

        if(dishId < 1)
            throw new InvalidInputException();

        DishEntity entity = repository.findById(dishId).orElseThrow(() -> new NotFoundException());
        Dish dish = new Dish(entity.getRestaurantId(),entity.getDishId(), entity.getName(), entity.getDescription(), entity.getPrice(), null);

        return just(dish);
    }

    @Override
    public Flux<Dish> getMenu(int restaurantId) {
        if (restaurantId < 1)
            throw new InvalidInputException();

        List<DishEntity> requestedMenu = repository.findByRestaurantId(restaurantId);
        List<Dish> menu = requestedMenu.stream().map(e -> new Dish(e.getRestaurantId(), e.getDishId(), e.getName(), e.getDescription(), e.getPrice(), null)).collect(Collectors.toList());

        return Flux.fromIterable(menu);
    }

    @Override
    public Dish createDish(Dish dish) {
        DishEntity entity = new DishEntity(dish.getRestaurantId(), dish.getDishId(), dish.getName(),dish.getDescription(),dish.getPrice());
        DishEntity save = repository.save(entity);
        Dish savedDish = new Dish(dish.getRestaurantId(), save.getDishId(), save.getName(), save.getDescription(), save.getPrice(), null);
        return savedDish;
    }


}
