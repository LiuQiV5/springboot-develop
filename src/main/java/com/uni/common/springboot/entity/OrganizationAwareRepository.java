package com.uni.common.springboot.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrganizationAwareRepository<T, Long> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {
}

