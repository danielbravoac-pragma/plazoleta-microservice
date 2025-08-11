package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.StatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IStatusEntityMapper {

    Status toStatus(StatusEntity statusEntity);

    StatusEntity toStatusEntity(Status status);
}