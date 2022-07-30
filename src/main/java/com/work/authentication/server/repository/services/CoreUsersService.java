package com.work.authentication.server.repository.services;

import com.querydsl.core.types.Predicate;
import com.work.authentication.server.entity.CoreUser;

import java.util.Optional;

public interface CoreUsersService {
    Optional<CoreUser> findById(Long id);
    CoreUser createOrUpdate(CoreUser coreUser);
    CoreUser findByusername(String username);
//    Optional<CoreUser> findOne(Predicate predicate);
}
