package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.StatusEntity;

public interface IStatusRepository extends IGenericRepository<StatusEntity, Long> {

    StatusEntity findByName(String name);
}
