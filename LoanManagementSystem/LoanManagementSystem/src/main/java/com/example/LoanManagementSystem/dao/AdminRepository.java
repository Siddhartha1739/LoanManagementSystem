package com.example.LoanManagementSystem.dao;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
//    boolean existByName(String name);

    Optional<Admin> findByName(String name);
}
