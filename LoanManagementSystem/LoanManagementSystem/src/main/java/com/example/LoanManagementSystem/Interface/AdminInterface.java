package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.models.AdminModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminInterface {

     ResponseEntity<?> viewAdmin(Long adminId);
     ResponseEntity<?> updateAdmin(Long adminId, AdminModel admin);
    ResponseEntity<?> approveLoans(Long loanApprovalId, Long adminId, LoanApproval loanApproval);
}
