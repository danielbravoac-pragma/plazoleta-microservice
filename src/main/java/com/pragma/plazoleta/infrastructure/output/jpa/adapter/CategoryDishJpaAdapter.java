package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.ICategoryDishPersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishId;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryDishJpaAdapter implements ICategoryDishPersistencePort {

    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;

    private final ICategoryDishRepository categoryDishRepository;

    @Override
    public void saveCategoryDish(Category category, Dish dish) {
        CategoryDishId id = new CategoryDishId(category.getId(), dish.getId());
        CategoryDishEntity relation = new CategoryDishEntity();
        relation.setId(id);
        relation.setCategory(categoryEntityMapper.toCategoryEntity(category));
        relation.setDish(dishEntityMapper.toDishEntity(dish));

        categoryDishRepository.save(relation);
    }
}
