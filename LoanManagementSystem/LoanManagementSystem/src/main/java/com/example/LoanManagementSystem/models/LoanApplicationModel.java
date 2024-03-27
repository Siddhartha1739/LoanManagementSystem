package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

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
