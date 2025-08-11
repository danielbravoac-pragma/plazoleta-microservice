package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.CategoriesRequest;
import com.pragma.plazoleta.application.dto.request.CreateDishRequest;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.response.CreateDishResponse;
import com.pragma.plazoleta.application.dto.response.UpdateDishResponse;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

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

    List<CreateDishResponse> toCreateDishList(List<Dish> dishList);
}
