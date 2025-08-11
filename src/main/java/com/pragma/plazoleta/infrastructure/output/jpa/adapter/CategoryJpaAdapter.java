package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Category findById(Long id) {
        return categoryEntityMapper.toCategory(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("Category not found"))
        );
    }
}
