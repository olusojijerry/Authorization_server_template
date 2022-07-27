package com.work.authentication.server.repository.services;

import com.querydsl.core.types.Predicate;
import com.work.authentication.server.entity.CoreUser;
import com.work.authentication.server.repository.CoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CoreUserServiceImpl implements CoreUsersService{
    @Autowired
    CoreUserRepository coreUserRepository;
    @Override
    public Optional<CoreUser> findById(Long id) {
        return coreUserRepository.findById(id);
    }

    @Override
    public CoreUser createOrUpdate(CoreUser coreUser) {
        return coreUserRepository.save(coreUser);
    }

    @Override
    public CoreUser findByusername(String username) {
        return coreUserRepository.findByUsername(username);
    }

    @Override
    public Optional<CoreUser> findOne(Predicate predicate) {
        return coreUserRepository.findOne(predicate);
    }
}
