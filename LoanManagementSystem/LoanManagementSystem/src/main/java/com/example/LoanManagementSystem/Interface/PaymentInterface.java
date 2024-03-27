package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.Payment;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.models.PaymentModel;
import org.springframework.http.ResponseEntity;

import javax.print.DocFlavor;

public interface PaymentInterface {
    String makePayment(PaymentModel paymentModel, Long loanId);
    ResponseEntity<?> getPaymentById(Long paymentId);
    double calculateMonthlyInstallment(double totalAmount, double months);
    void closeLoan(Loan loan);
    void   calculateLoan(double amount, double apr, double months, Loan loan, Payment payment, PaymentSchedule paymentSchedule, double installment);
    double skipMonthPenaulty(Loan loan,Payment payment,double amount);
}
