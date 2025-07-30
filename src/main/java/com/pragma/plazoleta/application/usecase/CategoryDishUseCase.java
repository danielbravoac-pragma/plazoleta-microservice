package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.api.ICategoryDishServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.ICategoryDishPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryDishUseCase implements ICategoryDishServicePort {

    private final ICategoryDishPersistencePort categoryDishPersistencePort;

    @Override
    public void saveCategoryDish(Category category, Dish dish) {
        categoryDishPersistencePort.saveCategoryDish(category, dish);
    }
}
