package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantEntityMapper.toRestaurant(
                restaurantRepository.save(
                        restaurantEntityMapper.toRestaurantEntity(restaurant)
                )
        );
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantEntityMapper.toRestaurant(
                restaurantRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Restaurant not found"))
        );
    }

    @Override
    public Page<Restaurant> findAllPageable(Integer page, Integer size) {
        Page<RestaurantEntity> pageRestaurantEntity=restaurantRepository.findAll(PageRequest.of(page, size, Sort.by("name").ascending()));
        return new Page<Restaurant>() {
            @Override
            public int getTotalPages() {
                return pageRestaurantEntity.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return pageRestaurantEntity.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super Restaurant, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return pageRestaurantEntity.getNumber();
            }

            @Override
            public int getSize() {
                return pageRestaurantEntity.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Restaurant> getContent() {
                return restaurantEntityMapper.toRestaurantPage(pageRestaurantEntity.getContent());
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return pageRestaurantEntity.isLast();
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Restaurant> iterator() {
                return null;
            }
        };
    }
}
