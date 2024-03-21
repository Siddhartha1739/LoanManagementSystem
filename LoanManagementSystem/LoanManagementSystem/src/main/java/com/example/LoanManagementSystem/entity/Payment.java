package com.example.LoanManagementSystem.entity;

import com.example.LoanManagementSystem.models.LoanModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.Date;

@Data
@Entity
public class Payment {
    @Id
    private Long paymentId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loanId")
    @JsonBackReference
    private Loan loan;
    private Date paymentDate;
    private double amount;

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}
