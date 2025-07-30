package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateDishRequest;
import com.pragma.plazoleta.application.dto.CreateDishResponse;
import com.pragma.plazoleta.application.dto.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.UpdateDishResponse;
import com.pragma.plazoleta.application.mapper.DishMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final DishMapper dishMapper;

    @Override
    public CreateDishResponse saveDish(CreateDishRequest createDishRequest) {
        return dishMapper.toCreateDishResponse(
                dishServicePort.saveDish(
                        dishMapper.toCreateDish(createDishRequest)
                )
        );
    }

    @Override
    public UpdateDishResponse updateDish(UpdateDishRequest updateDishRequest) {
        return dishMapper.toUpdateDishResponse(
                dishServicePort.updateDish(
                        dishMapper.toUpdateDish(updateDishRequest)
                )
        );
    }
}
