package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantPersistencePort.findById(id);
    }
}
