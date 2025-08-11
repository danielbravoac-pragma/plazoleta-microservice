package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    private Dish dish;
    private DishEntity dishEntity;

    @BeforeEach
    void setUp() {
        dish = new Dish();
        dish.setId(1L);
        dish.setName("Pizza");

        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setName("Pizza");
    }

    @Test
    void saveDish_ShouldReturnSavedDish() {
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);
        when(dishEntityMapper.toDish(dishEntity)).thenReturn(dish);

        Dish result = dishJpaAdapter.saveDish(dish);

        assertEquals("Pizza", result.getName());
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void updateDish_ShouldCallFindByIdAndSave() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toDish(dishEntity)).thenReturn(dish);
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        Dish result = dishJpaAdapter.updateDish(dish);

        assertEquals(1L, result.getId());
        verify(dishRepository).findById(1L);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void findById_ShouldReturnDish_WhenExists() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toDish(dishEntity)).thenReturn(dish);

        Dish result = dishJpaAdapter.findById(1L);

        assertEquals("Pizza", result.getName());
        verify(dishRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowDataNotFound_WhenNotExists() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> dishJpaAdapter.findById(1L));
        verify(dishRepository).findById(1L);
    }
}
