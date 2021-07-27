package io.github.marbys.microservices.dish.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.microservices.dish.persistence.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)

    })
    DishEntity dishToDishEntity(Dish dish);

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true),
    })
    Dish dishEntityToDish(DishEntity entity);
}
