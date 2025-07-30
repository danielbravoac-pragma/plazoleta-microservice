package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;

public interface IRestaurantHandler {
    CreateRestaurantResponse saveRestaurant(CreateRestaurantRequest createRestaurantRequest);
}
