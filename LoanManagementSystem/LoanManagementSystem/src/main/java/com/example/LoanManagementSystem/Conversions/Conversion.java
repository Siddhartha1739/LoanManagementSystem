package com.example.LoanManagementSystem.Conversions;

import com.example.LoanManagementSystem.entity.*;
import com.example.LoanManagementSystem.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class Conversion {
    @Autowired
    PasswordEncoder passwordEncoder;
    public Admin ModelToEntityAdmin(AdminModel adminModel){
        Admin admin=new Admin();
        admin.setAdminId(adminModel.getAdminId());
        admin.setName(adminModel.getName());
        admin.setEmail(adminModel.getEmail());
        admin.setPassword(passwordEncoder.encode(adminModel.getPassword()));
        admin.setRole(adminModel.getRole());
        admin.setLoanApprovalList(adminModel.getLoanApproval());
        admin.setPhoneNumber(adminModel.getPhoneNumber());
        return admin;
    }
    public AdminModel EntityToModelAdmin(Admin admin){
        AdminModel adminModel=new AdminModel();
        adminModel.setAdminId(admin.getAdminId());
        adminModel.setName(admin.getName());
        adminModel.setEmail(admin.getEmail());
        adminModel.setPassword(admin.getPassword());
        adminModel.setRole(admin.getRole());
        adminModel.setLoanApproval(admin.getLoanApprovalList());
        adminModel.setPhoneNumber(admin.getPhoneNumber());
        return adminModel;
    }

    public LoanApplicationModel EntityToModelLoanApplication(LoanApplication loanApplication){
        LoanApplicationModel loanApplicationModel=new LoanApplicationModel();
        loanApplicationModel.setLoanApplicationId(loanApplication.getLoanApplicationId());
        loanApplicationModel.setApplicationDate(loanApplication.getApplicationDate());
        loanApplicationModel.setStatus(loanApplication.getStatus());
        loanApplicationModel.setSalary(loanApplication.getSalary());
        loanApplicationModel.setUser(loanApplication.getUser());
        loanApplicationModel.setLoanApproval(loanApplication.getLoanApproval());
        loanApplicationModel.setAmountRequested(loanApplication.getAmountRequested());
        return loanApplicationModel;
    }
    public LoanApplication ModelToEntityLoanApplication(LoanApplicationModel loanApplicationModel){
        LoanApplication loanApplication=new LoanApplication();
        loanApplication.setLoanApplicationId(loanApplicationModel.getLoanApplicationId());
        loanApplication.setApplicationDate(loanApplicationModel.getApplicationDate());
        loanApplication.setStatus(loanApplicationModel.getStatus());
        loanApplication.setSalary(loanApplicationModel.getSalary());
        loanApplication.setUser(loanApplicationModel.getUser());
        loanApplication.setLoanApproval(loanApplicationModel.getLoanApproval());
        loanApplication.setAmountRequested(loanApplicationModel.getAmountRequested());
        return loanApplication;
    }

    public LoanApproval ModelToEntityLoanApproval(LoanApprovalModel loanApprovalModel){
        LoanApproval loanApproval=new LoanApproval();
        loanApproval.setLoanApprovalId(loanApprovalModel.getLoanApprovalId());
        loanApproval.setApproveDate(loanApprovalModel.getApproveDate());
        loanApproval.setApproveStatus(loanApprovalModel.getApproveStatus());
        loanApproval.setLoanApplication(loanApprovalModel.getLoanApplication());
        loanApproval.setAdmin(loanApprovalModel.getAdmin());
        return loanApproval;
    }
    public LoanApprovalModel EntityToModelLoanApproval(LoanApproval loanApproval){
        LoanApprovalModel loanApprovalModel=new LoanApprovalModel();
        loanApprovalModel.setLoanApprovalId(loanApproval.getLoanApprovalId());
        loanApprovalModel.setApproveDate(loanApproval.getApproveDate());
        loanApprovalModel.setApproveStatus(loanApproval.getApproveStatus());
        loanApprovalModel.setLoanApplication(loanApproval.getLoanApplication());
        loanApprovalModel.setAdmin(loanApproval.getAdmin());
        return loanApprovalModel;
    }

    public LoanModel EntityToModelLoan(Loan loan){
        LoanModel loanModel=new LoanModel();
        loanModel.setLoanId(loan.getLoanId());
        loanModel.setLoanName(loan.getLoanName());
        loanModel.setAmount(loan.getAmount());
        loanModel.setInterestRate(loan.getInterestRate());
        loanModel.setStartDate(loan.getStartDate());
        loanModel.setEndDate(loan.getEndDate());
        loanModel.setStatus(loan.getStatus());
        loanModel.setMonths(loan.getMonths());
        loanModel.setUsers(loan.getUsers());
        loanModel.setPayments(loan.getPayments());
        loanModel.setPaymentSchedule(loan.getPaymentSchedule());
        loanModel.setInstallmentAmount(loan.getInstallmentAmount());
        loanModel.setRemainingAmount(loan.getRemainingAmount());
        return loanModel;
    }

    public Loan ModelToEntityLoan(LoanModel loanModel){
        Loan loan=new Loan();
        loan.setLoanId(loanModel.getLoanId());
        loan.setLoanName(loanModel.getLoanName());
        loan.setAmount(loanModel.getAmount());
        loan.setInterestRate(loanModel.getInterestRate());
        loan.setStartDate(loanModel.getStartDate());
        loan.setEndDate(loanModel.getEndDate());
        loan.setStatus(loanModel.getStatus());
        loan.setMonths(loanModel.getMonths());
        loan.setUsers(loanModel.getUsers());
        loan.setPayments(loanModel.getPayments());
        loan.setPaymentSchedule(loanModel.getPaymentSchedule());
        loan.setInstallmentAmount(loanModel.getInstallmentAmount());
        loan.setRemainingAmount(loanModel.getRemainingAmount());
        return loan;
    }

    public PaymentScheduleModel EntityToModelPaymentSchedule(PaymentSchedule paymentSchedule){
        PaymentScheduleModel paymentScheduleModel=new PaymentScheduleModel();
        paymentScheduleModel.setPaymentScheduleId(paymentSchedule.getPaymentScheduleId());
        paymentScheduleModel.setDueDate(paymentSchedule.getDueDate());
        paymentScheduleModel.setInstallmentAmount(paymentSchedule.getInstallmentAmount());
        paymentScheduleModel.setPaid(paymentSchedule.isPaid());
        paymentScheduleModel.setLoan(paymentSchedule.getLoan());
        return paymentScheduleModel;
    }

    public   PaymentSchedule ModelToEntityPaymentSchedule(PaymentScheduleModel paymentScheduleModel){
        PaymentSchedule paymentSchedule=new PaymentSchedule();
        paymentSchedule.setDueDate(paymentScheduleModel.getDueDate());
        paymentSchedule.setPaymentScheduleId(paymentScheduleModel.getPaymentScheduleId());
        paymentSchedule.setInstallmentAmount(paymentScheduleModel.getInstallmentAmount());
        paymentSchedule.setPaid(paymentScheduleModel.isPaid());
        paymentSchedule.setLoan(paymentScheduleModel.getLoan());
        return paymentSchedule;
    }

    public PaymentModel EntityToModelPayment(Payment payment){
        PaymentModel paymentModel=new PaymentModel();
        paymentModel.setPaymentId(payment.getPaymentId());
        paymentModel.setPaymentDate(payment.getPaymentDate());
        paymentModel.setAmount(payment.getAmount());
        paymentModel.setLoan(payment.getLoan());
        return paymentModel;
    }
    public Payment ModelToEntityPayment(PaymentModel paymentModel){
        Payment payment=new Payment();
        payment.setPaymentId(paymentModel.getPaymentId());
        payment.setPaymentDate(paymentModel.getPaymentDate());
        payment.setAmount(paymentModel.getAmount());
        payment.setLoan(payment.getLoan());
        return payment;
    }

    public User ModelToEntityUser(UserModel userModel){
        User user=new User();
        user.setUserId(userModel.getUserId());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setAddress(userModel.getAddress());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setLoanApplications(userModel.getLoanApplications());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(userModel.getRole());
        user.setLoans(userModel.getLoans());
        return user;
    }
    public UserModel EntityToModelUser(User user){
        UserModel userModel=new UserModel();
        userModel.setUserId(user.getUserId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setAddress(user.getAddress());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setLoanApplications(user.getLoanApplications());
        userModel.setLoans(user.getLoans());
        userModel.setRole(user.getRole());
        userModel.setPassword(user.getPassword());
        return userModel;
    }
    public LocalDate DateToLocalDate(Date date){
        Date convDate =date;
        Instant instant = convDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
