package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.models.LoanApplicationModel;
import org.springframework.http.ResponseEntity;

public interface LoanApplicationInterface {
     ResponseEntity<?> applyForLoan(LoanApplicationModel loanApplicationModel, Long userId);
     ResponseEntity<?> getLoanApplicationById(Long loanApplicationId);
     ResponseEntity<?> updateLoanApplication(Long loanApplicationId, LoanApplication loanApplication);
     void cancelLoanApplication(Long loanApplicationId);
}
