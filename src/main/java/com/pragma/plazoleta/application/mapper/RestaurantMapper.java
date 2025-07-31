package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    Restaurant toCreateRestaurant(CreateRestaurantRequest createRestaurantRequest);

    CreateRestaurantResponse toCreateRestaurantResponse(Restaurant restaurant);
}
