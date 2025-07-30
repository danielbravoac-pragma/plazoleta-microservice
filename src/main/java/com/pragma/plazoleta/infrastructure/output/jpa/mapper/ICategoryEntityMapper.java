package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    Category toCategory(CategoryEntity categoryEntity);

    CategoryEntity toCategoryEntity(Category category);

}
