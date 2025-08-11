package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateDishRequest;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.response.CreateDishResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.UpdateDishResponse;
import com.pragma.plazoleta.application.mapper.DishMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishHandlerTest {

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishHandler dishHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDish() {
        CreateDishRequest request = new CreateDishRequest();
        Dish domainDish = new Dish();
        Dish savedDish = new Dish();
        CreateDishResponse expectedResponse = new CreateDishResponse();

        when(dishMapper.toCreateDish(request)).thenReturn(domainDish);
        when(dishServicePort.saveDish(domainDish)).thenReturn(savedDish);
        when(dishMapper.toCreateDishResponse(savedDish)).thenReturn(expectedResponse);

        CreateDishResponse actualResponse = dishHandler.saveDish(request);

        assertEquals(expectedResponse, actualResponse);
        verify(dishMapper).toCreateDish(request);
        verify(dishServicePort).saveDish(domainDish);
        verify(dishMapper).toCreateDishResponse(savedDish);
    }

    @Test
    void testUpdateDish() {
        UpdateDishRequest request = new UpdateDishRequest();
        Dish updatedDish = new Dish();
        Dish returnedDish = new Dish();
        UpdateDishResponse expectedResponse = new UpdateDishResponse();

        when(dishMapper.toUpdateDish(request)).thenReturn(updatedDish);
        when(dishServicePort.updateDish(updatedDish)).thenReturn(returnedDish);
        when(dishMapper.toUpdateDishResponse(returnedDish)).thenReturn(expectedResponse);

        UpdateDishResponse actualResponse = dishHandler.updateDish(request);

        assertEquals(expectedResponse, actualResponse);
        verify(dishMapper).toUpdateDish(request);
        verify(dishServicePort).updateDish(updatedDish);
        verify(dishMapper).toUpdateDishResponse(returnedDish);
    }

    @Test
    void testEnableDish() {
        Long dishId = 1L;
        Dish returnedDish = new Dish();
        UpdateDishResponse expectedResponse = new UpdateDishResponse();

        when(dishServicePort.activeUnactiveDish(dishId, true)).thenReturn(returnedDish);
        when(dishMapper.toUpdateDishResponse(returnedDish)).thenReturn(expectedResponse);

        UpdateDishResponse actualResponse = dishHandler.enableDish(dishId);

        assertEquals(expectedResponse, actualResponse);
        verify(dishServicePort).activeUnactiveDish(dishId, true);
        verify(dishMapper).toUpdateDishResponse(returnedDish);
    }

    @Test
    void testDisableDish() {
        Long dishId = 2L;

        dishHandler.disableDish(dishId);

        verify(dishServicePort).activeUnactiveDish(dishId, false);
    }

    @Test
    void testFindDishesByRestaurantAndOptionalCategory() {
        Long categoryId = 1L;
        Long restaurantId = 1L;
        int page = 0;
        int size = 2;

        Dish dish1 = new Dish();
        Dish dish2 = new Dish();
        Page<Dish> pageDish = new PageImpl<>(List.of(dish1, dish2), PageRequest.of(page, size), 2);

        CreateDishResponse res1 = new CreateDishResponse();
        CreateDishResponse res2 = new CreateDishResponse();

        when(dishServicePort.findDishesByRestaurantAndOptionalCategory(categoryId, restaurantId, page, size)).thenReturn(pageDish);
        when(dishMapper.toCreateDishList(List.of(dish1, dish2))).thenReturn(List.of(res1, res2));

        PageResponse<CreateDishResponse> result = dishHandler.findDishesByRestaurantAndOptionalCategory(categoryId, restaurantId, page, size);

        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.getContent().containsAll(List.of(res1, res2)));

        verify(dishServicePort).findDishesByRestaurantAndOptionalCategory(categoryId, restaurantId, page, size);
        verify(dishMapper).toCreateDishList(List.of(dish1, dish2));
    }
}
