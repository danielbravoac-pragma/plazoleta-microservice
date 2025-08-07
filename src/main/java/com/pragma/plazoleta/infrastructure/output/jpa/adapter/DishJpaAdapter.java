package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public Dish saveDish(Dish dish) {
        return dishEntityMapper.toDish(
                dishRepository.save(
                        dishEntityMapper.toDishEntity(dish)
                )
        );
    }

    @Override
    public Dish updateDish(Dish dish) {
        findById(dish.getId());

        return dishEntityMapper.toDish(
                dishRepository.save(
                        dishEntityMapper.toDishEntity(dish)
                )
        );
    }

    @Override
    public Dish findById(Long id) {
        return dishEntityMapper.toDish(
                dishRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Dish not found"))
        );
    }

    @Override
    public Dish activeUnactiveDish(Long id, Boolean status) {
        Dish dishUpdating = findById(id);
        dishUpdating.setIsActive(status);
        return dishEntityMapper.toDish(
                dishRepository.save(dishEntityMapper.toDishEntity(dishUpdating))
        );
    }

    @Override
    public Page<Dish> findDishesByRestaurantAndOptionalCategory(Long idCategory, Long idRestaurant, Integer page, Integer size) {
        Page<DishEntity> dishesByRestaurantAndCategory = dishRepository.findDishesByRestaurantAndOptionalCategory(idRestaurant, idCategory, PageRequest.of(page, size, Sort.by("name").ascending()));
        return new Page<Dish>() {
            @Override
            public int getTotalPages() {
                return dishesByRestaurantAndCategory.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return dishesByRestaurantAndCategory.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super Dish, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return dishesByRestaurantAndCategory.getNumber();
            }

            @Override
            public int getSize() {
                return dishesByRestaurantAndCategory.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Dish> getContent() {
                return dishEntityMapper.dishEntityListToDishList(dishesByRestaurantAndCategory.getContent());
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
                return dishesByRestaurantAndCategory.isLast();
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
            public Iterator<Dish> iterator() {
                return null;
            }
        };
    }
}
