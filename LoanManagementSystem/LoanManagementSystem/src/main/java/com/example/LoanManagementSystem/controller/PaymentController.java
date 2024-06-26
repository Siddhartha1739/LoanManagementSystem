package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.Interface.PaymentInterface;
import com.example.LoanManagementSystem.entity.Payment;
import com.example.LoanManagementSystem.models.PaymentModel;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import com.example.LoanManagementSystem.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentInterface paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@Valid @RequestBody PaymentModel paymentModel, @RequestParam Long loanId) {
        String newPayment = paymentService.makePayment(paymentModel,loanId);
        return new ResponseEntity<>(newPayment, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPayment(@RequestParam Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }



}
