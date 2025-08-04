package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateDishRequest;
import com.pragma.plazoleta.application.dto.CreateDishResponse;
import com.pragma.plazoleta.application.dto.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.UpdateDishResponse;

public interface IDishHandler {
    CreateDishResponse saveDish(CreateDishRequest createDishRequest);

    UpdateDishResponse updateDish(UpdateDishRequest updateDishRequest);

    UpdateDishResponse enableDish(Long id);

    void disableDish(Long id);
}
