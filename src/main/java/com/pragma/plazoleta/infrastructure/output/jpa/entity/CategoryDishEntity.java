package com.pragma.plazoleta.infrastructure.output.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "category_dish")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDishEntity {

    @EmbeddedId
    private CategoryDishId id;

    @ManyToOne
    @MapsId("categoryId")
    private CategoryEntity category;

    @ManyToOne
    @MapsId("dishId")
    private DishEntity dish;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedAt;
}
