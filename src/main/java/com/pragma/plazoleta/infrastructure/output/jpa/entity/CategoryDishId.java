package com.pragma.plazoleta.infrastructure.output.jpa.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryDishId implements Serializable {
    private Long categoryId;
    private Long dishId;
}
