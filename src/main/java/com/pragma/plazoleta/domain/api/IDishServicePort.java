package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Dish;

public interface IDishServicePort {
    Dish saveDish(Dish dish);

    Dish updateDish(Dish dish);

    Dish findById(Long id);

    Dish activeUnactiveDish(Long id, Boolean status);
}
