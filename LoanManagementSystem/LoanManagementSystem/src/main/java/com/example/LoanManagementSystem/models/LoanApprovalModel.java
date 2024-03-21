package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.swing.*;
import java.util.Date;

@Data
public class LoanApprovalModel {
    @NotNull(message = "Loan Approve Id is Required")
    private Long loanApprovalId;
    private LoanApplication loanApplication;
    private Admin admin;
    @NotNull(message = "Approve Date is Required")
    private Date approveDate;
    private ApproveStatus approveStatus;

    @Override
    public String toString() {
        return "LoanApproval{" +
                "loanApprovalId=" + loanApprovalId +
                ", approveDate=" + approveDate +
                ", approveStatus=" + approveStatus +
                '}';
    }
}
