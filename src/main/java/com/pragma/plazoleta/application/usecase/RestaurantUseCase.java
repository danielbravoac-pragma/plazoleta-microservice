package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IUserServicePort userServicePort;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());
        restaurant.setOwnerId(loggedUserId);

        User user = userServicePort.findById(loggedUserId);

        if(user == null || !user.getRoles().contains("OWNER")){
            throw new AccessDeniedException("Solo propietarios válidos pueden crear platos.");
        }

        return restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantPersistencePort.findById(id);
    }
}
