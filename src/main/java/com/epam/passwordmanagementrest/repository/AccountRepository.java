package com.epam.passwordmanagementrest.repository;

import com.epam.passwordmanagementrest.entity.Account;
import com.epam.passwordmanagementrest.entity.Group;
import com.epam.passwordmanagementrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUser(User user);

    Optional<Account> findByUrlAndUser(String url, User user);

    List<Account> findByGroupAndUser(Group existGroup, User user);

    Optional<Account> findByIdAndUser(int id, User user);

    void deleteByIdAndUser(int id, User user);

    void deleteByUrlAndUser(String url, User user);
}
