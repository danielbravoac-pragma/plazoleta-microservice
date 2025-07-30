package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.api.ICategoryDishServicePort;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    private final ICategoryDishServicePort categoryDishServicePort;

    private final IRestaurantServicePort restaurantServicePort;

    private final ICategoryServicePort categoryServicePort;

    @Override
    public Dish saveDish(Dish dish) {

        Restaurant restaurant = restaurantServicePort.findById(dish.getRestaurant().getId());
        dish.setRestaurant(restaurant);

        Dish savedDish = dishPersistencePort.saveDish(dish);

        dish.getCategories().forEach(categoryRequest -> {
            Category categoryFull = categoryServicePort.findById(categoryRequest.getId());
            categoryDishServicePort.saveCategoryDish(categoryFull, savedDish);
        });

        return savedDish;
    }

    @Override
    public Dish updateDish(Dish dish) {
        Dish dishUpdate = findById(dish.getId());
        dishUpdate.setPrice(dish.getPrice());
        dishUpdate.setDescription(dish.getDescription());

        return dishPersistencePort.updateDish(dishUpdate);
    }

    @Override
    public Dish findById(Long id) {
        return dishPersistencePort.findById(id);
    }
}
