package com.pragma.plazoleta.infrastructure.output.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderStatusId {
    private Long orderId;
    private Long statusId;
}
