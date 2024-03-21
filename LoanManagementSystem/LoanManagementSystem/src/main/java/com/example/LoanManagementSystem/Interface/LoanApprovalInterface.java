package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.models.LoanApprovalModel;
import org.springframework.http.ResponseEntity;

public interface LoanApprovalInterface {
    ResponseEntity<?> approveLoan(LoanApprovalModel loanApprovalModel, Long adminId, Long loanApplicationId);
     ResponseEntity<?> getLoanApprovalById(Long loanApprovalId);
    ResponseEntity<?> updateLoanApproval(Long loanApprovalId, LoanApproval loanApproval);
    ResponseEntity<?> cancelLoanApproval(Long loanApprovalId);

}
