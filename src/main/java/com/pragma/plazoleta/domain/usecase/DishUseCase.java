package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    private final ICategoryDishServicePort categoryDishServicePort;

    private final IRestaurantServicePort restaurantServicePort;

    private final ICategoryServicePort categoryServicePort;

    private final IUserServicePort userServicePort;

    @Override
    public Dish saveDish(Dish dish) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());

        Restaurant restaurant = restaurantServicePort.findById(dish.getRestaurant().getId());
        dish.setRestaurant(restaurant);

        User user = userServicePort.findById(loggedUserId);

        if (user == null || !user.getRoles().contains("OWNER")) {
            throw new AccessDeniedException("Solo propietarios válidos pueden crear platos.");
        }

        if (!restaurant.getOwnerId().equals(loggedUserId)) {
            throw new AccessDeniedException("No puedes crear platos en un restaurante que no es tuyo");
        }

        Dish savedDish = dishPersistencePort.saveDish(dish);

        dish.getCategories().forEach(categoryRequest -> {
            Category categoryFull = categoryServicePort.findById(categoryRequest.getId());
            categoryDishServicePort.saveCategoryDish(categoryFull, savedDish);
        });

        return savedDish;
    }

    @Override
    public Dish updateDish(Dish dish) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());

        Dish dishUpdate = findById(dish.getId());
        dishUpdate.setPrice(dish.getPrice());
        dishUpdate.setDescription(dish.getDescription());

        User user = userServicePort.findById(loggedUserId);

        if (user == null || !user.getRoles().contains("OWNER")) {
            throw new AccessDeniedException("Solo propietarios válidos pueden actualizar platos.");
        }

        if (!dishUpdate.getRestaurant().getOwnerId().equals(loggedUserId)) {
            throw new AccessDeniedException("No puedes actualizar platos en un restaurante que no es tuyo");
        }

        return dishPersistencePort.updateDish(dishUpdate);
    }

    @Override
    public Dish findById(Long id) {
        return dishPersistencePort.findById(id);
    }

    @Override
    public Dish activeUnactiveDish(Long id, Boolean status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());

        User user = userServicePort.findById(loggedUserId);

        if (user == null || !user.getRoles().contains("OWNER")) {
            throw new AccessDeniedException("Solo propietarios válidos pueden actualizar platos.");
        }

        Dish dishUpdate = findById(id);

        if (!dishUpdate.getRestaurant().getOwnerId().equals(loggedUserId)) {
            throw new AccessDeniedException("No puedes actualizar platos en un restaurante que no es tuyo");
        }

        return dishPersistencePort.activeUnactiveDish(id, status);
    }

    @Override
    public Page<Dish> findDishesByRestaurantAndOptionalCategory(Long idCategory, Long idRestaurant, Integer page, Integer size) {
        return dishPersistencePort.findDishesByRestaurantAndOptionalCategory(idCategory, idRestaurant, page, size);
    }
}
