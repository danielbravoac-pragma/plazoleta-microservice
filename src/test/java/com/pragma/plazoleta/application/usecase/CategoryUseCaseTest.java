package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import com.pragma.plazoleta.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void findById_ShouldReturnCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Bebidas");

        when(categoryPersistencePort.findById(1L)).thenReturn(category);

        Category result = categoryUseCase.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Bebidas", result.getName());
        verify(categoryPersistencePort).findById(1L);
    }
}
