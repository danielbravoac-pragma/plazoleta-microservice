package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishId;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryDishJpaAdapterTest {

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @Mock
    private ICategoryDishRepository categoryDishRepository;

    @InjectMocks
    private CategoryDishJpaAdapter categoryDishJpaAdapter;

    private Category category;
    private Dish dish;
    private CategoryEntity categoryEntity;
    private DishEntity dishEntity;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Postres");

        dish = new Dish();
        dish.setId(2L);
        dish.setName("Torta");

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Postres");

        dishEntity = new DishEntity();
        dishEntity.setId(2L);
        dishEntity.setName("Torta");
    }

    @Test
    void saveCategoryDish_ShouldMapAndSaveRelation() {
        when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);

        categoryDishJpaAdapter.saveCategoryDish(category, dish);

        ArgumentCaptor<CategoryDishEntity> captor = ArgumentCaptor.forClass(CategoryDishEntity.class);
        verify(categoryDishRepository).save(captor.capture());

        CategoryDishEntity savedRelation = captor.getValue();

        // Verificar que la relación contiene los IDs correctos
        CategoryDishId relationId = savedRelation.getId();
        assertEquals(1L, relationId.getCategoryId());
        assertEquals(2L, relationId.getDishId());

        // Verificar que los objetos mapeados son los que se pasaron
        assertEquals("Postres", savedRelation.getCategory().getName());
        assertEquals("Torta", savedRelation.getDish().getName());
    }
}
