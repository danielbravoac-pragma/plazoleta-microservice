package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantRepository extends IGenericRepository<RestaurantEntity, Long> {
    Page<RestaurantEntity> findAll(Pageable pageable);
}
