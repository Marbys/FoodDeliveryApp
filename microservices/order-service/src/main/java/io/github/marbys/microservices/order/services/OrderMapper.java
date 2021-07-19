package io.github.marbys.microservices.order.services;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.order.Order;
import io.github.marbys.microservices.order.persistence.OrderEntity;
import io.github.marbys.microservices.order.persistence.RequestedDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)

    })
    Order entityToApi(OrderEntity entity);


    DishSummary dishToDishSummary(RequestedDish dish);

    List<DishSummary> dishListToDishSummaryList(List<RequestedDish> dish);
    List<RequestedDish> dishSummaryListToRequestedDishList(List<DishSummary> dishSummaries);

}
