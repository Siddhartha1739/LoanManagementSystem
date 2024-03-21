package com.example.LoanManagementSystem.entity;

import com.example.LoanManagementSystem.models.ApproveStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class LoanApproval {
    @Id
    private Long loanApprovalId;
    @JsonBackReference
    @OneToOne(mappedBy ="loanApproval",cascade = CascadeType.ALL)
    private LoanApplication loanApplication;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="adminId")
    @JsonIgnore
    private Admin admin;
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
