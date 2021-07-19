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
    private final DishMapper mapper;

    @Autowired
    public DishServiceImpl(DishRepository repository, ServiceUtil serviceUtil, DishMapper mapper) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
    }

    @Override
    public Mono<Dish> getDish(int dishId) {

        if(dishId < 1)
            throw new InvalidInputException("Invalid dishId: " + dishId);

        DishEntity entity = repository.findByDishId(dishId).orElseThrow(() -> new NotFoundException("No dish found for dishId: " + dishId));
        Dish dish = mapper.dishEntityToDish(entity);
        dish.setServiceAddress(serviceUtil.getServiceAddress());

        return just(dish);
    }

    @Override
    public Flux<Dish> getMenu(int restaurantId) {
        if (restaurantId < 1)
            throw new InvalidInputException("Invalid restaurantId: " + restaurantId);

        List<DishEntity> requestedMenu = repository.findByRestaurantId(restaurantId);
        List<Dish> menu = requestedMenu.stream().map(e -> mapper.dishEntityToDish(e)).collect(Collectors.toList());
        menu.forEach(s -> s.setServiceAddress(serviceUtil.getServiceAddress()));

        return Flux.fromIterable(menu);
    }

    @Override
    public Dish createDish(Dish dish) {
        DishEntity entity = mapper.dishToDishEntity(dish);
        DishEntity savedEntity = repository.save(entity);
        Dish savedDish = mapper.dishEntityToDish(savedEntity);
        return savedDish;
    }


}
