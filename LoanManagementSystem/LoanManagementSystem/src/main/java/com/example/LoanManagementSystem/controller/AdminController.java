package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.Interface.AdminInterface;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.service.AdminService;
import com.example.LoanManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminInterface adminService;

    @GetMapping("/viewAdmin")
    public ResponseEntity<?> viewAdmin(@RequestParam Long adminId){
        return adminService.viewAdmin(adminId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestParam Long adminId,@RequestBody AdminModel admin){
        return adminService.updateAdmin(adminId, admin);
    }

    @PutMapping("/approve")
    public ResponseEntity<?> approveLoans(@RequestParam Long loanApprovalId, @RequestParam Long adminId, @RequestBody LoanApproval loanApproval){
        return adminService.approveLoans(loanApprovalId,adminId,loanApproval);
    }

}
