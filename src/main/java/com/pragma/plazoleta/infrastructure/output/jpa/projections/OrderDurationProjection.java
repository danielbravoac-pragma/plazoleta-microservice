package com.pragma.plazoleta.infrastructure.output.jpa.projections;

public interface OrderDurationProjection {
    Long getOrderId();
    Long getDurationSeconds();
}
