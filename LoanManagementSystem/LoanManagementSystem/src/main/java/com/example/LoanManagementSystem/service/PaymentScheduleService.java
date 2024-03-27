package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.PaymentScheduleInterface;
import com.example.LoanManagementSystem.dao.PaymentScheduleRepository;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import com.example.LoanManagementSystem.models.UserModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentScheduleService implements PaymentScheduleInterface {

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private Conversion conversion;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getPaymentScheduleById(Long paymentScheduleId) {
        Optional<PaymentSchedule> paymentSchedule = paymentScheduleRepository.findById(paymentScheduleId);
        if (paymentSchedule.isPresent()) {
            PaymentScheduleModel paymentScheduleModel = conversion.EntityToModelPaymentSchedule(paymentScheduleRepository.findById(paymentScheduleId).orElse(null));
            return new ResponseEntity<>(paymentScheduleModel, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PaymentSchedule Id not Found");
    }

    @Override
    public ResponseEntity<?> getPaymentScheduleByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserModel userModel=conversion.EntityToModelUser(user.get());
            List<PaymentSchedule> paymentScheduleList=userModel.getLoans().stream()
                    .flatMap(loan -> loan.getPaymentSchedule().stream())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(paymentScheduleList, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not Found");
    }

}

