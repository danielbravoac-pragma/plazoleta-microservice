package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.*;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    Dish toCreateDish(CreateDishRequest createDishRequest);

    Dish toUpdateDish(UpdateDishRequest updateDishRequest);

    CreateDishResponse toCreateDishResponse(Dish dish);

    UpdateDishResponse toUpdateDishResponse(Dish dish);

    @Mapping(source = "idCategory", target = "id")
    Category toCategory(CategoriesRequest request);
}
