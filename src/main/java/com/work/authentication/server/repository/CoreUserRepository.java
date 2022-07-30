package com.work.authentication.server.repository;

import com.work.authentication.server.entity.CoreUser;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CoreUserRepository extends PagingAndSortingRepository<CoreUser, Long> {
    CoreUser findByUsername(String username);
}
