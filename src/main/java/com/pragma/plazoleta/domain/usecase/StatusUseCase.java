package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IStatusServicePort;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.spi.IStatusPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusUseCase implements IStatusServicePort {

    private final IStatusPersistencePort statusPersistencePort;

    @Override
    public Status findByName(String name) {
        return statusPersistencePort.findByName(name);
    }
}
