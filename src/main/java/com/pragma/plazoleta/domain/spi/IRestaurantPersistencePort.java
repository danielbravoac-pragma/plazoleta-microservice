package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantPersistencePort {
    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findById(Long id);

    Page<Restaurant> findAllPageable(Integer page, Integer size);
}
