package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.User;

public interface IUserPersistencePort {
    User findById(Long id);
}
