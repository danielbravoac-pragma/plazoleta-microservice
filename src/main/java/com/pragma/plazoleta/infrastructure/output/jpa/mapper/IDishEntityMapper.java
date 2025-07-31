package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishId;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    @Mapping(target = "restaurant.dishes", ignore = true)
    Dish toDish(DishEntity dishEntity);

    DishEntity toDishEntity(Dish dish);

    List<Dish> dishEntityListToDishList(List<DishEntity> entities);

    default Long map(CategoryDishId id) {
        return id != null ? id.getCategoryId() : null;
    }

    default CategoryDishId map(Long id) {
        if (id == null) return null;
        CategoryDishId categoryDishId = new CategoryDishId();
        categoryDishId.setCategoryId(id);
        return categoryDishId;
    }

}
