package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;

    public Category(Long id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    private String name;
}
