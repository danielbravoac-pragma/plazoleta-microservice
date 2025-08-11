package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Status;

public interface IStatusServicePort {
    Status findByName(String name);

    Status findById(Long id);
}
