package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.LoanApplicationInterface;
import com.example.LoanManagementSystem.dao.LoanApplicationRepository;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.ApproveStatus;
import com.example.LoanManagementSystem.models.LoanApplicationModel;
import com.example.LoanManagementSystem.models.LoanStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PrimaryKeyJoinColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationService implements LoanApplicationInterface {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Conversion conversion;


    @Override
    public ResponseEntity<?> applyForLoan(LoanApplicationModel loanApplicationModel,Long userId) {

        LoanApplication loanApplication =conversion.ModelToEntityLoanApplication(loanApplicationModel);

      double AmountRequested= loanApplication.getAmountRequested();

      double maxLoanAmount = AmountRequested * 0.1;

       if (loanApplication.getSalary() < maxLoanAmount) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount requested exceeds 10% of monthly salary");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


       loanApplication.setUser(user);
       loanApplication.setStatus(LoanStatus.ACTIVE);

        List<LoanApplication> loanApplicationList=user.getLoanApplications();
        loanApplicationList.add(loanApplication);
        user.setLoanApplications(loanApplicationList);

        userRepository.save(user);
       return new ResponseEntity<>(loanApplicationRepository.save(loanApplication),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplicationOptional = loanApplicationRepository.findById(loanApplicationId);

        if (loanApplicationOptional.isPresent()) {
            LoanApplicationModel loanApplicationModel = conversion.EntityToModelLoanApplication(loanApplicationOptional.get());
            return new ResponseEntity<>(loanApplicationModel, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Application Id not found");
        }
    }

    @Override
    public ResponseEntity<?> updateLoanApplication(Long loanApplicationId, LoanApplication loanApplication) {
        LoanApplication existingLoanApplication = loanApplicationRepository.findById(loanApplicationId).orElse(null);
        if (null != existingLoanApplication ) {

            existingLoanApplication.setAmountRequested(loanApplication.getAmountRequested());
            existingLoanApplication.setApplicationDate(loanApplication.getApplicationDate());
            existingLoanApplication.setStatus(loanApplication.getStatus());

            return new ResponseEntity<>(loanApplicationRepository.save(existingLoanApplication),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan Application not found");
    }


    @Override
    public void cancelLoanApplication(Long loanApplicationId) {
        try {
            LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                    .orElseThrow(() -> new EntityNotFoundException("Loan Application not found"));

            if (loanApplication.getLoanApproval().getApproveStatus() != ApproveStatus.APPROVED) {
                loanApplicationRepository.deleteById(loanApplicationId);
            } else {
                throw new IllegalStateException("Cannot cancel a loan application that has been approved");
            }
        } catch (NullPointerException e) {
            throw new IllegalStateException("Loan Approval is null");
        }
    }

}