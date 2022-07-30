package com.work.authentication.server.repository;

import com.work.authentication.server.entity.CoreRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CoreRolesRepository extends PagingAndSortingRepository<CoreRole, Long> {
}
