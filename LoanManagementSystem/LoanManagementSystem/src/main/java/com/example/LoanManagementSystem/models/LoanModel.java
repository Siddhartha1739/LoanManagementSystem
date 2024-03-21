package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Payment;
import com.example.LoanManagementSystem.entity.PaymentSchedule;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.validations.ValidMonths;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class  LoanModel {
    @NotNull(message = "Loan Id is Required")
    private Long loanId;
    @NotBlank(message = "Loan Name is Required")
    private String loanName;
    private User users;
    private double amount;
    @ValidMonths
    private double months;
    private double interestRate;
    private Date startDate;
    private Date endDate;
    private double installmentAmount;
    private double remainingAmount;
    private List<Payment> payments=new ArrayList<>();
    private LoanStatus status;
    private List<PaymentSchedule> paymentSchedule=new ArrayList<>();

    @Override
    public String toString() {
        return "LoanModel{" +
                "loanId=" + loanId +
                ", loanName='" + loanName + '\'' +
                ", amount=" + amount +
                ", months=" + months +
                ", interestRate=" + interestRate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", installmentAmount=" + installmentAmount +
                ", remainingAmount=" + remainingAmount +
                ", status=" + status +
                '}';
    }
}
