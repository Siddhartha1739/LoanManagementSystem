package com.example.LoanManagementSystem.dao;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Long> {
}
