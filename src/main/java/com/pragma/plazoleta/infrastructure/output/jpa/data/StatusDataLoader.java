package com.pragma.plazoleta.infrastructure.output.jpa.data;

import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.model.StatusEnum;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.StatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusDataLoader implements CommandLineRunner {
    private final IStatusRepository statusRepository;
    private final IStatusEntityMapper statusEntityMapper;

    @Override
    public void run(String... args) throws Exception {
        if (statusRepository.count() == 0) {
            statusRepository.save(statusEntityMapper.toStatusEntity(new Status(StatusEnum.IN_PROGRESS)));
            statusRepository.save(statusEntityMapper.toStatusEntity(new Status(StatusEnum.PENDING)));
            statusRepository.save(statusEntityMapper.toStatusEntity(new Status(StatusEnum.CANCELLED)));
            statusRepository.save(statusEntityMapper.toStatusEntity(new Status(StatusEnum.DONE)));
            statusRepository.save(statusEntityMapper.toStatusEntity(new Status(StatusEnum.DELIVERED)));
        }
    }
}
