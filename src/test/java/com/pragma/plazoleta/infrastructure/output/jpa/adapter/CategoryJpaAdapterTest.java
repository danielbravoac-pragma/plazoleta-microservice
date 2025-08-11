package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Postres");

        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Postres");
    }

    @Test
    void findById_ShouldReturnCategory_WhenExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryJpaAdapter.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Postres", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowDataNotFound_WhenNotExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> categoryJpaAdapter.findById(1L));
        verify(categoryRepository).findById(1L);
    }
}
