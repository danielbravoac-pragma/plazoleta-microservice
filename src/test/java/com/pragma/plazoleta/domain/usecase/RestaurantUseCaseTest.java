package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.model.UserRole;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userServicePort);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void saveRestaurant_shouldSaveWhenUserIsAdmin() {
        Long userId = 1L;
        Restaurant restaurant = new Restaurant();
        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(10L);

        User user = new User();
        user.setId(userId);
        user.setRoles(List.of(UserRole.ADMINISTRATOR.toString()));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(userServicePort.findById(userId)).thenReturn(user);
        when(restaurantPersistencePort.saveRestaurant(restaurant)).thenReturn(savedRestaurant);

        Restaurant result = restaurantUseCase.saveRestaurant(restaurant);

        assertEquals(10L, result.getId());
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_shouldThrowAccessDeniedWhenUserIsNotAdmin() {
        Long userId = 2L;
        Restaurant restaurant = new Restaurant();

        User user = new User();
        user.setId(userId);
        user.setRoles(List.of("CUSTOMER"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(userServicePort.findById(userId)).thenReturn(user);

        assertThrows(AccessDeniedException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurant_shouldThrowAccessDeniedWhenUserIsNull() {
        Long userId = 3L;
        Restaurant restaurant = new Restaurant();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(userServicePort.findById(userId)).thenReturn(null);

        assertThrows(AccessDeniedException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void findById_shouldReturnRestaurant() {
        Long id = 5L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);

        when(restaurantPersistencePort.findById(id)).thenReturn(restaurant);

        Restaurant result = restaurantUseCase.findById(id);

        assertEquals(id, result.getId());
        verify(restaurantPersistencePort).findById(id);
    }

    @Test
    void findAll_shouldReturnPageOfRestaurants() {
        int page = 0;
        int size = 2;
        Page<Restaurant> expectedPage = new PageImpl<>(List.of(new Restaurant(), new Restaurant()));

        when(restaurantPersistencePort.findAllPageable(page, size)).thenReturn(expectedPage);

        Page<Restaurant> result = restaurantUseCase.findAll(page, size);

        assertEquals(2, result.getContent().size());
        verify(restaurantPersistencePort).findAllPageable(page, size);
    }
}
