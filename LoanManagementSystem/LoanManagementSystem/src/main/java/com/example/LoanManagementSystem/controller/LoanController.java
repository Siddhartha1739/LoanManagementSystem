package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.Interface.LoanInterface;
import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.models.LoanModel;
import com.example.LoanManagementSystem.service.LoanService;
import com.example.LoanManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanInterface loanService;

    @PostMapping("/createLoan")
    public ResponseEntity<?> createLoan(@Valid @RequestBody LoanModel loanModel, @RequestParam Long loanApplicationId) {
        return loanService.createLoan(loanModel,loanApplicationId);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getLoan(@RequestParam Long loanId) {
        return loanService.getLoanById(loanId);}

    @PutMapping("/update")
    public ResponseEntity<?> updateLoan(@RequestParam Long loanId, @RequestBody LoanModel loan) {
        return loanService.updateLoan(loanId, loan);
    }


}

