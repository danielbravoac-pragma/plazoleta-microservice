package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.spi.IStatusPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusJpaAdapter implements IStatusPersistencePort {

    private final IStatusRepository statusRepository;
    private final IStatusEntityMapper statusEntityMapper;

    @Override
    public Status findByName(String name) {
        return statusEntityMapper.toStatus(statusRepository.findByName(name));
    }

    @Override
    public Status findById(Long id) {
        return statusEntityMapper.toStatus(statusRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Status not Found")));
    }
}
