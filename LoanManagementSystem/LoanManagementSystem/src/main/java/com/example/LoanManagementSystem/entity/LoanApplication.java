package com.example.LoanManagementSystem.entity;

import com.example.LoanManagementSystem.models.LoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Data
@Entity
public class LoanApplication {
    @Id
    private Long loanApplicationId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    @JsonBackReference
    private User user;
    private double amountRequested;
    private double salary;
    private Date applicationDate;
    private LoanStatus status;
    @OneToOne
    private LoanApproval loanApproval;

    @Override
    public String toString() {
        return "LoanApplication{" +
                "loanApplicationId=" + loanApplicationId +
                ", amountRequested=" + amountRequested +
                ", salary=" + salary +
                ", applicationDate=" + applicationDate +
                ", status=" + status +
                '}';
    }
}
