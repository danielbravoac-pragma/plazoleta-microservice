package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.CreateRestaurantResponse;
import com.pragma.plazoleta.application.dto.response.PageRestaurantResponse;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    Restaurant toCreateRestaurant(CreateRestaurantRequest createRestaurantRequest);

    CreateRestaurantResponse toCreateRestaurantResponse(Restaurant restaurant);

    List<PageRestaurantResponse> toPageRestaurantResponse(List<Restaurant> pageRestaurant);
}
