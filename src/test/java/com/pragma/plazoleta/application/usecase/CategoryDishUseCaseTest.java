package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.ICategoryDishPersistencePort;
import com.pragma.plazoleta.domain.usecase.CategoryDishUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryDishUseCaseTest {

    @Mock
    private ICategoryDishPersistencePort categoryDishPersistencePort;

    @InjectMocks
    private CategoryDishUseCase categoryDishUseCase;

    @Test
    void saveCategoryDish_ShouldCallPersistencePort() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Postres");

        Dish dish = new Dish();
        dish.setId(2L);
        dish.setName("Torta de Chocolate");

        categoryDishUseCase.saveCategoryDish(category, dish);

        verify(categoryDishPersistencePort).saveCategoryDish(category, dish);
    }
}
