package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.application.mapper.RestaurantMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private CreateRestaurantRequest createRestaurantRequest;
    private CreateRestaurantResponse createRestaurantResponse;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        createRestaurantRequest = new CreateRestaurantRequest();
        createRestaurantRequest.setName("Restaurante Test");
        createRestaurantRequest.setAddress("Av. Principal 123");

        createRestaurantResponse = new CreateRestaurantResponse("Restaurante Test","1234564894891","Address","+57998181981",2L);

        restaurant = new Restaurant();
        restaurant.setName("Restaurante Test");
        restaurant.setAddress("Av. Principal 123");
    }

    @Test
    void saveRestaurant_ShouldReturnCreateRestaurantResponse() {
        when(restaurantMapper.toCreateRestaurant(createRestaurantRequest)).thenReturn(restaurant);
        when(restaurantServicePort.saveRestaurant(any(Restaurant.class))).thenReturn(restaurant);
        when(restaurantMapper.toCreateRestaurantResponse(restaurant)).thenReturn(createRestaurantResponse);

        CreateRestaurantResponse response = restaurantHandler.saveRestaurant(createRestaurantRequest);


        assertEquals("Restaurante Test", response.getName());
    }
}
