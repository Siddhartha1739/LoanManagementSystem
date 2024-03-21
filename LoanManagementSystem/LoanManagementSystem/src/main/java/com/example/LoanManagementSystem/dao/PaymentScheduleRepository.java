package com.example.LoanManagementSystem.dao;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule,Long> {
}
