package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.models.LoanApplicationModel;
import com.example.LoanManagementSystem.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loanApplications")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping("/applyLoan")
    public ResponseEntity<?> applyForLoan(@Valid @RequestBody LoanApplicationModel loanApplicationModel, @RequestParam Long userId) {
        return loanApplicationService.applyForLoan(loanApplicationModel,userId);
    }

    @GetMapping("/getLoan")
    public ResponseEntity<?> getLoanApplication(@RequestParam Long loanApplicationId) {
        return loanApplicationService.getLoanApplicationById(loanApplicationId);

    }

    @PutMapping("/updateLoan")
    public ResponseEntity<?> updateLoanApplication(@RequestParam Long loanApplicationId, @RequestBody LoanApplication loanApplication) {
        return loanApplicationService.updateLoanApplication(loanApplicationId, loanApplication);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> cancelLoanApplication(@RequestParam Long loanApplicationId) {
        loanApplicationService.cancelLoanApplication(loanApplicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
