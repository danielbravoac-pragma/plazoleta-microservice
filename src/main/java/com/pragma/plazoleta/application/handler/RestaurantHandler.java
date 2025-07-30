package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.application.mapper.RestaurantMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantMapper restaurantMapper;

    @Override
    public CreateRestaurantResponse saveRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        return restaurantMapper.toCreateRestaurantResponse(
                restaurantServicePort.saveRestaurant(
                        restaurantMapper.toCreateRestaurant(createRestaurantRequest)
                )
        );
    }
}
