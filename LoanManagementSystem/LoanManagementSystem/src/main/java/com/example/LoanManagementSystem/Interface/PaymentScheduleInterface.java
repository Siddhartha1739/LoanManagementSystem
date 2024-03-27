package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import org.springframework.http.ResponseEntity;

public interface PaymentScheduleInterface {
    ResponseEntity<?> getPaymentScheduleById(Long paymentScheduleId);
    ResponseEntity<?> getPaymentScheduleByUser(Long userId);
}
