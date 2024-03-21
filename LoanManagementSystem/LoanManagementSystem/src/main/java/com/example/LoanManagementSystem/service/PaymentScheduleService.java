package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.PaymentScheduleInterface;
import com.example.LoanManagementSystem.dao.PaymentScheduleRepository;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentScheduleService implements PaymentScheduleInterface {

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private Conversion conversion;

    @Override
    public ResponseEntity<?> getPaymentScheduleById(Long paymentScheduleId) {
        Optional<PaymentSchedule> paymentSchedule=paymentScheduleRepository.findById(paymentScheduleId);
        if (paymentSchedule.isPresent()) {
            PaymentScheduleModel paymentScheduleModel = conversion.EntityToModelPaymentSchedule(paymentScheduleRepository.findById(paymentScheduleId).orElse(null));
            return new ResponseEntity<>(paymentScheduleModel, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PaymentSchedule Id not Found");
    }

    @Override
    public ResponseEntity<?> updatePaymentSchedule(Long paymentScheduleId, PaymentSchedule paymentSchedule) {
        PaymentSchedule existingPaymentSchedule = paymentScheduleRepository.findById(paymentScheduleId).orElse(null);
        if (existingPaymentSchedule != null && paymentSchedule != null) {
            existingPaymentSchedule.setDueDate(paymentSchedule.getDueDate());
            existingPaymentSchedule.setInstallmentAmount(paymentSchedule.getInstallmentAmount());
            existingPaymentSchedule.setPaid(paymentSchedule.isPaid());

            return new ResponseEntity<>(paymentScheduleRepository.save(existingPaymentSchedule),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("payment Schedule Id "+paymentScheduleId+" not found");
    }

    @Override
    public void cancelPaymentSchedule(Long paymentScheduleId) {
        PaymentSchedule paymentSchedule = paymentScheduleRepository.findById(paymentScheduleId)
                .orElseThrow(() -> new EntityNotFoundException("PaymentSchedule Not found"));
        if (paymentSchedule != null) {
            paymentScheduleRepository.delete(paymentSchedule);
        }
    }
}
