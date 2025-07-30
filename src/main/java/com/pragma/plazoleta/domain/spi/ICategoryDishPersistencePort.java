package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;

public interface ICategoryDishPersistencePort {
    void saveCategoryDish(Category category, Dish dish);
}
