package com.example.LoanManagementSystem.controller;

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
    private PaymentScheduleService paymentScheduleService;

    @GetMapping("/get")
    public ResponseEntity<?> getPaymentSchedule(@RequestParam Long paymentScheduleId) {
        return paymentScheduleService.getPaymentScheduleById(paymentScheduleId);
    }

    @PutMapping("/put")
    public ResponseEntity<?> updatePaymentSchedule(@RequestParam Long paymentScheduleId, @RequestBody PaymentSchedule paymentSchedule) {
        return paymentScheduleService.updatePaymentSchedule(paymentScheduleId, paymentSchedule);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> cancelPaymentSchedule(@RequestParam Long paymentScheduleId) {
        paymentScheduleService.cancelPaymentSchedule(paymentScheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
