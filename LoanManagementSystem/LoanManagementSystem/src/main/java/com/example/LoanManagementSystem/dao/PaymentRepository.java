package com.example.LoanManagementSystem.dao;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
