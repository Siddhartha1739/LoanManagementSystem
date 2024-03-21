package com.example.LoanManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class PaymentSchedule {
    @Id
    private Long paymentScheduleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loanId")
    @JsonBackReference
    private Loan loan;
    private Date dueDate;
    private double installmentAmount;
    private boolean paid;

    @Override
    public String toString() {
        return "PaymentSchedule{" +
                "paymentScheduleId=" + paymentScheduleId +
                ", dueDate=" + dueDate +
                ", installmentAmount=" + installmentAmount +
                ", paid=" + paid +
                '}';
    }
}
