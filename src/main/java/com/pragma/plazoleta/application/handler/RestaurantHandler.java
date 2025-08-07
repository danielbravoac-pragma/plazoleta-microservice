package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.CreateRestaurantResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.PageRestaurantResponse;
import com.pragma.plazoleta.application.mapper.RestaurantMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantMapper restaurantMapper;

    @Override
    public CreateRestaurantResponse saveRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        return restaurantMapper.toCreateRestaurantResponse(
                restaurantServicePort.saveRestaurant(
                        restaurantMapper.toCreateRestaurant(createRestaurantRequest)
                )
        );
    }

    @Override
    public PageResponse<PageRestaurantResponse> findAll(Integer page, Integer size) {
        Page<Restaurant> pageRestaurant=restaurantServicePort.findAll(page,size);
        List<PageRestaurantResponse> content= restaurantMapper.toPageRestaurantResponse(pageRestaurant.getContent());

        PageResponse<PageRestaurantResponse> response=new PageResponse<>();
        response.setContent(content);
        response.setPage(pageRestaurant.getNumber());
        response.setSize(pageRestaurant.getSize());
        response.setTotalElements(pageRestaurant.getTotalElements());
        response.setTotalPages(pageRestaurant.getTotalPages());
        response.setLast(pageRestaurant.isLast());

        return response;
    }
}
