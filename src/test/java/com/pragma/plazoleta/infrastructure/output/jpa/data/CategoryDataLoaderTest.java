package com.pragma.plazoleta.infrastructure.output.jpa.data;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryDataLoaderTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryDataLoader categoryDataLoader;

    @Test
    void run_ShouldInsertCategories_WhenRepositoryIsEmpty() throws Exception {
        when(categoryRepository.count()).thenReturn(0L);
        when(categoryEntityMapper.toCategoryEntity(any(Category.class)))
                .thenAnswer(invocation -> {
                    Category category = invocation.getArgument(0);
                    CategoryEntity entity = new CategoryEntity();
                    entity.setName(category.getName());
                    return entity;
                });

        categoryDataLoader.run();

        verify(categoryRepository, times(3)).save(any(CategoryEntity.class));
    }

    @Test
    void run_ShouldNotInsert_WhenRepositoryHasData() throws Exception {
        when(categoryRepository.count()).thenReturn(5L);

        categoryDataLoader.run();

        verify(categoryRepository, never()).save(any());
    }
}
