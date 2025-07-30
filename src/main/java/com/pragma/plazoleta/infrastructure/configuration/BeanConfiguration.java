package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.application.usecase.CategoryDishUseCase;
import com.pragma.plazoleta.application.usecase.CategoryUseCase;
import com.pragma.plazoleta.application.usecase.DishUseCase;
import com.pragma.plazoleta.application.usecase.RestaurantUseCase;
import com.pragma.plazoleta.domain.api.ICategoryDishServicePort;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.spi.ICategoryDishPersistencePort;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.CategoryDishJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryDishRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IDishRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryDishRepository categoryDishRepository;


    @Bean
    public ICategoryDishPersistencePort categoryDishPersistencePort() {
        return new CategoryDishJpaAdapter(dishEntityMapper, categoryEntityMapper, categoryDishRepository);
    }

    @Bean
    public ICategoryDishServicePort categoryDishServicePort() {
        return new CategoryDishUseCase(categoryDishPersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }


    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), categoryDishServicePort(), restaurantServicePort(),categoryServicePort());
    }
}
