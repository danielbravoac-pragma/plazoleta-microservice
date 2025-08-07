package com.pragma.plazoleta.infrastructure.output.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusEntity {

    @EmbeddedId
    private OrderStatusId id;

    @ManyToOne
    @MapsId("orderId")
    private OrderEntity order;

    @ManyToOne
    @MapsId("statusId")
    private StatusEntity status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateRegistration;
}
