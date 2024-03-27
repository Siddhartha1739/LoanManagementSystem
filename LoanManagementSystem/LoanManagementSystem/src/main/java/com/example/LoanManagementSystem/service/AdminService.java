package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.AdminInterface;
import com.example.LoanManagementSystem.dao.AdminRepository;
import com.example.LoanManagementSystem.dao.LoanApprovalRepository;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.models.ApproveStatus;
import com.example.LoanManagementSystem.models.LoanApprovalModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements AdminInterface {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LoanApprovalRepository loanApprovalRepository;

    @Autowired
    private Conversion conversion;



    @Override
    public ResponseEntity<?> viewAdmin(Long adminId){
        Optional<Admin> admin= adminRepository.findById(adminId);
        if(admin.isPresent()){
            AdminModel adminModel=conversion.EntityToModelAdmin(admin.get());
            return new ResponseEntity<>(adminModel,HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Data found");
    }



    @Override
    public ResponseEntity<?> updateAdmin(Long adminId, AdminModel admin) {
        Admin existingAdmin = adminRepository.findById(adminId).orElse(null);
        if (  null!=existingAdmin && null!=admin) {
            Admin adminentity=conversion.ModelToEntityAdmin(admin);
            existingAdmin.setName(adminentity.getName());
            existingAdmin.setEmail(adminentity.getEmail());
            existingAdmin.setPhoneNumber(adminentity.getPhoneNumber());
            existingAdmin.setPassword(adminentity.getPassword());
            existingAdmin.setRole(adminentity.getRole());
            return new ResponseEntity<>(adminRepository.save(existingAdmin),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin  is not found");
    }


    @Override
    public ResponseEntity<?> approveLoans(Long loanApprovalId,Long adminId,LoanApproval loanApproval){

        if(loanApprovalRepository.existsById(loanApprovalId) && adminRepository.existsById(adminId)){
            LoanApproval loanApproval1=loanApprovalRepository.getReferenceById(loanApprovalId);
            Admin admin=adminRepository.getReferenceById(adminId);

            loanApproval1.setApproveStatus(loanApproval.getApproveStatus());
            List<LoanApproval> loanApprovalList=admin.getLoanApprovalList();

            loanApprovalList.add(loanApproval1);
            admin.setLoanApprovalList(loanApprovalList);

            loanApprovalRepository.save(loanApproval1);
            adminRepository.save(admin);
            if(loanApproval.getApproveStatus().equals(ApproveStatus.APPROVED)){
                return new ResponseEntity<>("Loan is Approved",HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>("Loan is Rejected",HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Check loanapproval Id and admin Id");

    }


}
