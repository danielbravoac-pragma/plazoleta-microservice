package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;

public interface ICategoryDishServicePort {
    void saveCategoryDish(Category category, Dish dish);
}
