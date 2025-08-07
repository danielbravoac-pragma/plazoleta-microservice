package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDishRepository extends IGenericRepository<DishEntity, Long> {

    @Query("""
            SELECT d FROM DishEntity d
            JOIN CategoryDishEntity cd ON d.id = cd.dish.id
            WHERE d.restaurant.id = :restaurantId
            AND (:categoryId IS NULL OR cd.category.id = :categoryId)
            """)
    Page<DishEntity> findDishesByRestaurantAndOptionalCategory(
            @Param("restaurantId") Long restaurantId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
