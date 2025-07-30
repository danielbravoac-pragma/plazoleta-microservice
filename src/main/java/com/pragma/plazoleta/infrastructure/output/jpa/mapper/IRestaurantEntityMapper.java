package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishId;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    RestaurantEntity toRestaurantEntity(Restaurant restaurant);
}
