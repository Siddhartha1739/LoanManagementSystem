package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.models.LoanApprovalModel;
import com.example.LoanManagementSystem.service.LoanApprovalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loanApprovals")
public class LoanApprovalController {

    @Autowired
    private LoanApprovalService loanApprovalService;

    @PostMapping("/approveLoan")
    public ResponseEntity<?> approveLoan(@Valid @RequestBody LoanApprovalModel loanApprovalModel, @RequestParam Long adminId, @RequestParam Long loanApplicationId) {
        return loanApprovalService.approveLoan(loanApprovalModel,adminId,loanApplicationId);

    }

    @GetMapping("/getLoanApprovel")
    public ResponseEntity<?> getLoanApproval(@RequestParam Long loanApprovalId) {
        return loanApprovalService.getLoanApprovalById(loanApprovalId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLoanApproval(@RequestParam Long loanApprovalId, @RequestBody LoanApproval loanApproval) {
        return loanApprovalService.updateLoanApproval(loanApprovalId, loanApproval);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> cancelLoanApproval(@RequestParam Long loanApprovalId) {
        loanApprovalService.cancelLoanApproval(loanApprovalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
