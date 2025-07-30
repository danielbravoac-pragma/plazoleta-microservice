package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantEntityMapper.toRestaurant(
                restaurantRepository.save(
                        restaurantEntityMapper.toRestaurantEntity(restaurant)
                )
        );
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantEntityMapper.toRestaurant(
                restaurantRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Restaurant not found"))
        );
    }
}
