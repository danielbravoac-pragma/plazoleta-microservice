package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurante Test");

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Restaurante Test");
    }

    @Test
    void saveRestaurant_ShouldReturnSavedRestaurant() {
        when(restaurantEntityMapper.toRestaurantEntity(restaurant)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        Restaurant result = restaurantJpaAdapter.saveRestaurant(restaurant);

        assertEquals("Restaurante Test", result.getName());
        verify(restaurantRepository).save(restaurantEntity);
    }

    @Test
    void findById_ShouldReturnRestaurant_WhenExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(restaurantEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        Restaurant result = restaurantJpaAdapter.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Restaurante Test", result.getName());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowDataNotFound_WhenNotExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> restaurantJpaAdapter.findById(1L));
        verify(restaurantRepository).findById(1L);
    }
}
