package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PostMapping
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        return new ResponseEntity<>(restaurantHandler.saveRestaurant(createRestaurantRequest), HttpStatus.CREATED);
    }
}
