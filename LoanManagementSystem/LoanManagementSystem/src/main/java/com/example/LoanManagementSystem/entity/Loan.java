package com.example.LoanManagementSystem.entity;

import com.example.LoanManagementSystem.models.LoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Loan {
    @Id
    private Long loanId;
    private String loanName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    @JsonBackReference
    private User users;
    private double amount;
    private double interestRate;
    private double months;
    private Date startDate;
    private Date endDate;
    private double remainingAmount;
    private double installmentAmount;
    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> payments=new ArrayList<>();
    private LoanStatus status;
    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL)
    @JsonManagedReference
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
