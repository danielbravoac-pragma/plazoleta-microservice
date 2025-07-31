package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.CreateDishRequest;
import com.pragma.plazoleta.application.dto.CreateDishResponse;
import com.pragma.plazoleta.application.dto.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.UpdateDishResponse;
import com.pragma.plazoleta.application.mapper.DishMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishHandler dishHandler;

    private CreateDishRequest createDishRequest;
    private CreateDishResponse createDishResponse;
    private UpdateDishRequest updateDishRequest;
    private UpdateDishResponse updateDishResponse;
    private Dish dish;

    @BeforeEach
    void setUp() {
        createDishRequest = new CreateDishRequest();
        createDishRequest.setName("Pizza");


        createDishRequest.setPrice((int) 25.0);

        createDishResponse = new CreateDishResponse("Pizza",25,"Delicious Pizza");

        updateDishRequest = new UpdateDishRequest();
        updateDishRequest.setId(1L);
        updateDishRequest.setDescription("Pizza Actualizada");

        updateDishResponse = new UpdateDishResponse("Pizza Actualizada",35,"Pizza Deliciosa Actualizada");

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Pizza");
    }

    @Test
    void saveDish_ShouldReturnCreateDishResponse() {
        when(dishMapper.toCreateDish(createDishRequest)).thenReturn(dish);
        when(dishServicePort.saveDish(any(Dish.class))).thenReturn(dish);
        when(dishMapper.toCreateDishResponse(dish)).thenReturn(createDishResponse);

        CreateDishResponse response = dishHandler.saveDish(createDishRequest);

        assertEquals("Pizza", response.getName());
    }

    @Test
    void updateDish_ShouldReturnUpdateDishResponse() {
        when(dishMapper.toUpdateDish(updateDishRequest)).thenReturn(dish);
        when(dishServicePort.updateDish(any(Dish.class))).thenReturn(dish);
        when(dishMapper.toUpdateDishResponse(dish)).thenReturn(updateDishResponse);

        UpdateDishResponse response = dishHandler.updateDish(updateDishRequest);

        assertEquals("Pizza Actualizada", response.getName());
    }
}
