package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.LoanApprovalInterface;
import com.example.LoanManagementSystem.dao.AdminRepository;
import com.example.LoanManagementSystem.dao.LoanApplicationRepository;
import com.example.LoanManagementSystem.dao.LoanApprovalRepository;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.models.ApproveStatus;
import com.example.LoanManagementSystem.models.LoanApprovalModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApprovalService implements LoanApprovalInterface {

    @Autowired
    private LoanApprovalRepository loanApprovalRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private Conversion conversion;

    @Override
    public ResponseEntity<?> approveLoan(LoanApprovalModel loanApprovalModel, Long adminId, Long loanApplicationId) {

        LoanApproval loanApproval = conversion.ModelToEntityLoanApproval(loanApprovalModel);

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin with id " + adminId + " not found"));

        loanApproval.setApproveStatus(ApproveStatus.PENDING);
        loanApproval.setAdmin(admin);

        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new EntityNotFoundException("LoanApplication with id " + loanApplicationId + " not found"));

        loanApproval.setLoanApplication(loanApplication);

        LoanApproval savedLoanApproval = loanApprovalRepository.save(loanApproval);

        loanApplication.setLoanApproval(savedLoanApproval);
        loanApplicationRepository.save(loanApplication);

        return new ResponseEntity<>(savedLoanApproval,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> getLoanApprovalById(Long loanApprovalId) {
        Optional<LoanApproval> loanApproval=loanApprovalRepository.findById(loanApprovalId);
        if(loanApproval.isPresent()){
            LoanApprovalModel loanApprovalModel=conversion.EntityToModelLoanApproval(loanApprovalRepository.findById(loanApprovalId).orElse(null));
            return new ResponseEntity<>(loanApprovalModel,HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan Approval Id not found");
    }

    @Override
    public ResponseEntity<?> updateLoanApproval(Long loanApprovalId, LoanApproval loanApproval) {
        LoanApproval existingLoanApproval = loanApprovalRepository.findById(loanApprovalId).orElse(null);
        if ( null!=existingLoanApproval  && null!=loanApproval) {
            existingLoanApproval.setApproveDate(loanApproval.getApproveDate());
            existingLoanApproval.setApproveStatus(loanApproval.getApproveStatus());

            return new ResponseEntity<>(loanApprovalRepository.save(existingLoanApproval), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan Approval Id not found");
    }


    @Override
    public ResponseEntity<?> cancelLoanApproval(Long loanApprovalId) {
        boolean exists = loanApprovalRepository.existsById(loanApprovalId);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        loanApprovalRepository.deleteById(loanApprovalId);
        return ResponseEntity.ok().body("Loan approval with ID " + loanApprovalId + " has been successfully cancelled.");
    }


}