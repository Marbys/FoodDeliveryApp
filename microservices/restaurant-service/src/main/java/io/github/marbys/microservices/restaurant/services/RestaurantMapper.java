package io.github.marbys.microservices.restaurant.services;

import io.github.marbys.api.core.restaurant.Restaurant;
import io.github.marbys.microservices.restaurant.persistence.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    Restaurant entityToApi(RestaurantEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    RestaurantEntity apiToEntity(Restaurant api);
}
