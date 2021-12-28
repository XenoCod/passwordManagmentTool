package com.epam.passwordmanagementrest.repository;

import com.epam.passwordmanagementrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);
}
