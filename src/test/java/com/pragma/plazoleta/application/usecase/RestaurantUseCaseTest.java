package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;
    private User ownerUser;
    private User nonOwnerUser;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurante Test");

        ownerUser = new User();
        ownerUser.setId(10L);
        ownerUser.setRoles(List.of("OWNER"));

        nonOwnerUser = new User();
        nonOwnerUser.setId(11L);
        nonOwnerUser.setRoles(List.of("CUSTOMER"));
    }

    @Test
    void saveRestaurant_ShouldSave_WhenUserIsOwner() {
        // Simular autenticación con ID de propietario
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("10", null, List.of())
        );

        when(userServicePort.findById(10L)).thenReturn(ownerUser);
        when(restaurantPersistencePort.saveRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = restaurantUseCase.saveRestaurant(restaurant);

        assertEquals(10L, result.getOwnerId());
        assertEquals("Restaurante Test", result.getName());
    }

    @Test
    void saveRestaurant_ShouldThrowAccessDenied_WhenUserNotOwner() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("11", null, List.of())
        );

        when(userServicePort.findById(11L)).thenReturn(nonOwnerUser);

        assertThrows(AccessDeniedException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void findById_ShouldReturnRestaurant() {
        when(restaurantPersistencePort.findById(1L)).thenReturn(restaurant);

        Restaurant result = restaurantUseCase.findById(1L);

        assertEquals("Restaurante Test", result.getName());
        assertEquals(1L, result.getId());
    }
}
