package com.pragma.plazoleta.infrastructure.output.feign.adapter;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.infrastructure.output.feign.client.UserClient;
import com.pragma.plazoleta.infrastructure.output.feign.mapper.IUserResponseMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserFeignAdapter implements IUserPersistencePort {

    private final UserClient userClient;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public User findById(Long id) {
        return userResponseMapper.toUser(userClient.getUserById(id));
    }
}
