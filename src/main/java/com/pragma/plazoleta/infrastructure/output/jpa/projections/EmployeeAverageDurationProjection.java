package com.pragma.plazoleta.infrastructure.output.jpa.projections;

public interface EmployeeAverageDurationProjection {
    Long getEmployeeId();

    Long getAverageDurationSeconds();
}
