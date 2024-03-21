package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.LoanApplication;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentScheduleModel {
    private Long paymentScheduleId;
    private Loan loan;
    private Date dueDate;
    private double installmentAmount;
    private boolean paid;

    @Override
    public String toString() {
        return "PaymentScheduleModel{" +
                "paymentScheduleId=" + paymentScheduleId +
                ", dueDate=" + dueDate +
                ", installmentAmount=" + installmentAmount +
                ", paid=" + paid +
                '}';
    }
}
