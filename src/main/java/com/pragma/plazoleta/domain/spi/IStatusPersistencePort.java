package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Status;

public interface IStatusPersistencePort {
    Status findByName(String name);
}
