package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {
    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findById(Long id);

    Page<Restaurant> findAll(Integer page, Integer size);
}
