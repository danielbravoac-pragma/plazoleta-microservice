package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateDishRequest;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.response.CreateDishResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.UpdateDishResponse;

public interface IDishHandler {
    CreateDishResponse saveDish(CreateDishRequest createDishRequest);

    UpdateDishResponse updateDish(UpdateDishRequest updateDishRequest);

    UpdateDishResponse enableDish(Long id);

    void disableDish(Long id);

    PageResponse<CreateDishResponse> findDishesByRestaurantAndOptionalCategory(Long idCategory, Long idRestaurant, Integer page, Integer size);

}
