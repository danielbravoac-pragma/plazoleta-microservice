package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.CreateRestaurantResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.PageRestaurantResponse;

public interface IRestaurantHandler {
    CreateRestaurantResponse saveRestaurant(CreateRestaurantRequest createRestaurantRequest);

    PageResponse<PageRestaurantResponse> findAll(Integer page, Integer size);
}
