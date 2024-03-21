package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Data
public class LoanApplicationModel {
    @NotNull(message = "Loan Application Id is Required")
    private Long loanApplicationId;
    private User user;
    @NotNull(message = "Salary is Required")
    private double salary;
    @NotNull(message = "Amount id Required")
    private double amountRequested;
    @NotNull(message = "Appplication Date is Required")
    private Date applicationDate;
    private LoanStatus status;
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
