package com.epam.passwordmanagementrest.repository;

import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByNameAndUser(String name, User user);
    List<Group> findByUser(User user);
}
