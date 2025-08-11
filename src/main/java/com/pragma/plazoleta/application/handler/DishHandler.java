package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateDishRequest;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.response.CreateDishResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.UpdateDishResponse;
import com.pragma.plazoleta.application.mapper.DishMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UpdateDishResponse enableDish(Long id) {
        return dishMapper.toUpdateDishResponse(
                dishServicePort.activeUnactiveDish(id, Boolean.TRUE)
        );
    }

    @Override
    public void disableDish(Long id) {
        dishServicePort.activeUnactiveDish(id, Boolean.FALSE);
    }

    @Override
    public PageResponse<CreateDishResponse> findDishesByRestaurantAndOptionalCategory(Long idCategory, Long idRestaurant, Integer page, Integer size) {
        Page<Dish> pageDish = dishServicePort.findDishesByRestaurantAndOptionalCategory(idCategory, idRestaurant, page, size);
        List<CreateDishResponse> content = dishMapper.toCreateDishList(pageDish.getContent());

        PageResponse<CreateDishResponse> response = new PageResponse<>();
        response.setContent(content);
        response.setPage(pageDish.getNumber());
        response.setSize(pageDish.getSize());
        response.setTotalElements(pageDish.getTotalElements());
        response.setTotalPages(pageDish.getTotalPages());
        response.setLast(pageDish.isLast());

        return response;

    }
}
