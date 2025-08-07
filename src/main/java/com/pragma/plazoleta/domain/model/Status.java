package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private Long id;
    private StatusEnum name;

    public Status(StatusEnum name) {
        this.name = name;
    }
}
