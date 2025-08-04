package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

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
}
