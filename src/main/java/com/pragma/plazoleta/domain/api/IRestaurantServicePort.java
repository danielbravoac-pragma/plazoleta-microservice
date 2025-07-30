package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Restaurant;

public interface IRestaurantServicePort {
    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findById(Long id);
}
