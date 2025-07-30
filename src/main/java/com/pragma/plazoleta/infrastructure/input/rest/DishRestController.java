package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.CreateDishRequest;
import com.pragma.plazoleta.application.dto.CreateDishResponse;
import com.pragma.plazoleta.application.dto.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.UpdateDishResponse;
import com.pragma.plazoleta.application.handler.IDishHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @PostMapping
    public ResponseEntity<CreateDishResponse> saveDish(@RequestBody @Valid CreateDishRequest createDishRequest) {
        return new ResponseEntity<>(dishHandler.saveDish(createDishRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateDishResponse> updateDish(@RequestBody @Valid UpdateDishRequest updateDishRequest) {
        return new ResponseEntity<>(dishHandler.updateDish(updateDishRequest), HttpStatus.OK);
    }
}
