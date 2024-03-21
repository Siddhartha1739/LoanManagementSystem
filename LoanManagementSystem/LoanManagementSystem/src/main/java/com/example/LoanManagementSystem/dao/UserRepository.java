package com.example.LoanManagementSystem.dao;

import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByName(String name);

    Optional<User> findByName(String name);
}
