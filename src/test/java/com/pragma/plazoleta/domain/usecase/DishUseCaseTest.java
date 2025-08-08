package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.ICategoryDishServicePort;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.domain.usecase.DishUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private ICategoryDishServicePort categoryDishServicePort;
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private IUserServicePort userServicePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private Dish dish;
    private Restaurant restaurant;
    private User ownerUser;
    private User nonOwnerUser;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(10L);

        dish = new Dish();
        dish.setId(5L);
        dish.setName("Pizza");
        dish.setRestaurant(restaurant);
        dish.setCategories(new HashSet<>(Set.of(new Category(1L, "Italiana"))));

        ownerUser = new User();
        ownerUser.setId(10L);
        ownerUser.setRoles(List.of("OWNER"));

        nonOwnerUser = new User();
        nonOwnerUser.setId(20L);
        nonOwnerUser.setRoles(List.of("CUSTOMER"));
    }

    @Test
    void saveDish_ShouldSave_WhenUserIsOwnerAndOwnsRestaurant() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("10", null, List.of())
        );

        when(restaurantServicePort.findById(1L)).thenReturn(restaurant);
        when(userServicePort.findById(10L)).thenReturn(ownerUser);
        when(dishPersistencePort.saveDish(any(Dish.class))).thenReturn(dish);
        when(categoryServicePort.findById(1L)).thenReturn(new Category(1L, "Italiana"));

        Dish result = dishUseCase.saveDish(dish);

        assertEquals("Pizza", result.getName());
        verify(categoryDishServicePort).saveCategoryDish(any(Category.class), any(Dish.class));
    }

    @Test
    void saveDish_ShouldThrowAccessDenied_WhenUserNotOwner() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("20", null, List.of())
        );

        when(restaurantServicePort.findById(1L)).thenReturn(restaurant);
        when(userServicePort.findById(20L)).thenReturn(nonOwnerUser);

        assertThrows(AccessDeniedException.class, () -> dishUseCase.saveDish(dish));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldThrowAccessDenied_WhenRestaurantNotOwnedByUser() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("30", null, List.of())
        );

        User anotherOwner = new User();
        anotherOwner.setId(30L);
        anotherOwner.setRoles(List.of("OWNER"));

        when(restaurantServicePort.findById(1L)).thenReturn(restaurant);
        when(userServicePort.findById(30L)).thenReturn(anotherOwner);

        assertThrows(AccessDeniedException.class, () -> dishUseCase.saveDish(dish));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void updateDish_ShouldUpdate_WhenUserIsOwnerAndOwnsRestaurant() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("10", null, List.of())
        );

        Dish existingDish = new Dish();
        existingDish.setId(5L);
        existingDish.setName("Pizza");
        existingDish.setRestaurant(restaurant);

        dish.setPrice(30);
        dish.setDescription("Nueva desc");

        when(dishPersistencePort.findById(5L)).thenReturn(existingDish);
        when(userServicePort.findById(10L)).thenReturn(ownerUser);
        when(dishPersistencePort.updateDish(any(Dish.class))).thenReturn(existingDish);

        Dish result = dishUseCase.updateDish(dish);

        assertEquals("Pizza", result.getName());
        verify(dishPersistencePort).updateDish(any());
    }

    @Test
    void updateDish_ShouldThrowAccessDenied_WhenUserNotOwner() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("20", null, List.of())
        );

        Dish existingDish = new Dish();
        existingDish.setId(5L);
        existingDish.setRestaurant(restaurant);

        when(dishPersistencePort.findById(5L)).thenReturn(existingDish);
        when(userServicePort.findById(20L)).thenReturn(nonOwnerUser);

        assertThrows(AccessDeniedException.class, () -> dishUseCase.updateDish(dish));
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void findById_ShouldReturnDish() {
        when(dishPersistencePort.findById(5L)).thenReturn(dish);

        Dish result = dishUseCase.findById(5L);

        assertEquals("Pizza", result.getName());
    }
}
