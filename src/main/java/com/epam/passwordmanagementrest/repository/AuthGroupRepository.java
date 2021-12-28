package com.epam.passwordmanagementrest.repository;

import com.epam.passwordmanagementrest.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
    List<AuthGroup> findByUsername(String username);
}
