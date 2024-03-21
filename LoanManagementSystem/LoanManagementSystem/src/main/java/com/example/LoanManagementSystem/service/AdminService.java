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
    public ResponseEntity<?> addAdmin(AdminModel adminModel){
        Long adminID=adminModel.getAdminId();
        if (!adminRepository.existsById(adminID)){
            Admin admin=conversion.ModelToEntityAdmin(adminModel);
            adminRepository.save(admin);
            return new ResponseEntity<>("Admin added Successfully",HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin already exist");
    }

    @Override
    public ResponseEntity<?> viewAdmin(){
        List<Admin> AdminList= (List<Admin>) adminRepository.findAll();
        List<AdminModel> AdminModelList=new ArrayList<>();
        if(!AdminList.isEmpty()){
            AdminList.forEach(Admin -> {
                AdminModel AdminModel=conversion.EntityToModelAdmin(Admin);
                AdminModelList.add(AdminModel);
            });
            return new ResponseEntity<>(AdminModelList,HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Data found");
    }



    @Override
    public ResponseEntity<?> updateAdmin(Long adminId, Admin admin) {
        Admin existingAdmin = adminRepository.findById(adminId).orElse(null);
        if (  null!=existingAdmin && null!=admin) {
            existingAdmin.setName(admin.getName());
            existingAdmin.setEmail(admin.getEmail());
            existingAdmin.setPhoneNumber(admin.getPhoneNumber());
            return new ResponseEntity<>(adminRepository.save(existingAdmin),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin Id is not found");
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
