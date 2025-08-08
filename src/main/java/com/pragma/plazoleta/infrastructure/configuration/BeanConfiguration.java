package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.spi.*;
import com.pragma.plazoleta.domain.usecase.*;
import com.pragma.plazoleta.infrastructure.output.feign.adapter.UserFeignAdapter;
import com.pragma.plazoleta.infrastructure.output.feign.client.UserClient;
import com.pragma.plazoleta.infrastructure.output.feign.mapper.IUserResponseMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.*;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.*;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.*;
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
    private final UserClient userClient;
    private final IUserResponseMapper userResponseMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IStatusRepository statusRepository;
    private final IStatusEntityMapper statusEntityMapper;
    private final IOrderStatusRepository orderStatusRepository;
    private final IOrderStatusEntityMapper orderStatusEntityMapper;

    @Bean
    public IOrderStatusPersistencePort orderStatusPersistencePort() {
        return new OrderStatusJpaAdapter(orderEntityMapper, statusEntityMapper, orderStatusEntityMapper, orderStatusRepository);
    }

    @Bean
    public IOrderStatusServicePort orderStatusServicePort() {
        return new OrderStatusUseCase(orderStatusPersistencePort());
    }

    @Bean
    public IStatusPersistencePort statusPersistencePort() {
        return new StatusJpaAdapter(statusRepository, statusEntityMapper);
    }

    @Bean
    public IStatusServicePort statusServicePort() {
        return new StatusUseCase(statusPersistencePort());
    }


    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, restaurantServicePort());
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), dishServicePort(), restaurantServicePort(), statusServicePort(), orderStatusServicePort(), userServicePort());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserFeignAdapter(userClient, userResponseMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

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
        return new RestaurantUseCase(restaurantPersistencePort(), userServicePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), categoryDishServicePort(), restaurantServicePort(), categoryServicePort(), userServicePort());
    }
}
