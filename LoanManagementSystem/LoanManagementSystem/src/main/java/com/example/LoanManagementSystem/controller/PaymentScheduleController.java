package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.Interface.PaymentScheduleInterface;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import com.example.LoanManagementSystem.service.PaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentSchedules")
public class PaymentScheduleController {

    @Autowired
    PaymentScheduleInterface paymentScheduleService;

    @GetMapping("/get")
    public ResponseEntity<?> getPaymentSchedule(@RequestParam Long paymentScheduleId) {
        return paymentScheduleService.getPaymentScheduleById(paymentScheduleId);
    }
    @GetMapping("/getUserPayments")
    public ResponseEntity<?> getPaymentScheduleByUser(@RequestParam Long userId) {
        return paymentScheduleService.getPaymentScheduleByUser(userId);
    }

}
