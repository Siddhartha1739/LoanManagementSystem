package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.LoanModel;
import org.springframework.http.ResponseEntity;

import javax.print.DocFlavor;
import java.util.List;

public interface LoanInterface {
    ResponseEntity<?> createLoan(LoanModel loanModel, Long loanApplicationId);
    ResponseEntity<?> getLoanById(Long loanId);
    ResponseEntity<?> updateLoan(Long loanId, LoanModel loan);
    ResponseEntity<?> loanSanction(LoanApplication loanApplication, LoanModel loanModel, List<Loan> loansList, double LoanAmount, User user);
    void setDate(Loan loan, PaymentSchedule paymentSchedule);
    void setMonthlyInterest(Loan loan);
    void generatePaymentSchedule(Loan loan);
    Long generateNextId();
    double monthlyInstallment(double totalAmount, double months);
    double remainingAmount(double LoanAmount,double rate,double months);
}
