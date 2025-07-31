package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryDishId;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    RestaurantEntity toRestaurantEntity(Restaurant restaurant);

    @Mapping(target = "restaurant", ignore = true) // 🚨 clave para romper el ciclo
    Dish dishEntityToDish(DishEntity entity);

    // De entidad a modelo
    default Long map(CategoryDishId id) {
        return id != null ? id.getCategoryId() : null;
    }

    // De modelo a entidad
    default CategoryDishId map(Long id) {
        if (id == null) return null;
        CategoryDishId categoryDishId = new CategoryDishId();
        categoryDishId.setCategoryId(id);
        return categoryDishId;
    }
}
