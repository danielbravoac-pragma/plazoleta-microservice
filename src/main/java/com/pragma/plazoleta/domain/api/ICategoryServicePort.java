package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Category;

public interface ICategoryServicePort {
    Category findById(Long id);
}
