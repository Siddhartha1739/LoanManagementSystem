package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.LoanApprovalInterface;
import com.example.LoanManagementSystem.Interface.LoanInterface;
import com.example.LoanManagementSystem.dao.LoanApplicationRepository;
import com.example.LoanManagementSystem.dao.LoanRepository;
import com.example.LoanManagementSystem.dao.PaymentScheduleRepository;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.*;
import com.example.LoanManagementSystem.models.ApproveStatus;
import com.example.LoanManagementSystem.models.LoanModel;
import com.example.LoanManagementSystem.models.LoanStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LoanService implements LoanInterface {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private Conversion conversion;

    public static Long psId=1L;

    @Override
    public ResponseEntity<?> createLoan(LoanModel loanModel, Long loanApplicationId) {
        Optional<LoanApplication> loanApplication1=loanApplicationRepository.findById(loanApplicationId);
        Long loanID=loanModel.getLoanId();
        if (loanApplication1.isPresent() && !loanRepository.existsById(loanID)){
            LoanApplication loanApplication=loanApplicationRepository.getReferenceById(loanApplicationId);

            User user=loanApplication.getUser();
            List<Loan> loansList=user.getLoans();

            double LoanAmount=loanApplication.getAmountRequested();

            return loanSanction(loanApplication,loanModel,loansList,LoanAmount,user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Check the Loan Id and Loan Application Id ");
    }

    @Override
    public ResponseEntity<?> getLoanById(Long loanId) {
        Optional<Loan> loan=loanRepository.findById(loanId);
        if (loan.isPresent()){
            LoanModel loanModel=conversion.EntityToModelLoan(loan.get());
            return new ResponseEntity<>(loanModel,HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan Id not found");
    }


    @Override
    public ResponseEntity<?> updateLoan(Long loanId, LoanModel loan) {
        Loan existingLoan = loanRepository.findById(loanId).orElse(null);
        if (existingLoan != null && loan!=null) {

            double monthlyInterestRate;
            if(loan.getMonths()==3){
                monthlyInterestRate=2;
                existingLoan.setInterestRate(monthlyInterestRate);
            }if(loan.getMonths()==6){
                monthlyInterestRate=2;
                existingLoan.setInterestRate(monthlyInterestRate);
            }if(loan.getMonths()==9){
                monthlyInterestRate=2;
                existingLoan.setInterestRate(monthlyInterestRate);
            }if(loan.getMonths()==12){
                monthlyInterestRate =2;
                existingLoan.setInterestRate(monthlyInterestRate);
            }

            existingLoan.setLoanName(loan.getLoanName());
            existingLoan.setAmount(existingLoan.getAmount());
            existingLoan.setMonths(loan.getMonths());
            return new ResponseEntity<>(loanRepository.save(existingLoan),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan Id not Found");
    }

    @Override
    public ResponseEntity<?> loanSanction(LoanApplication loanApplication,LoanModel loanModel,List<Loan> loansList,double LoanAmount,User user){
        if(loanApplication.getLoanApproval().getApproveStatus()== ApproveStatus.APPROVED){
            PaymentSchedule paymentSchedule=new PaymentSchedule();
            Loan loan=conversion.ModelToEntityLoan(loanModel);

            setMonthlyInterest(loan);

            loan.setAmount(LoanAmount);
            double RemainingAmount=remainingAmount(LoanAmount,loan.getInterestRate(),loan.getMonths());
            loan.setRemainingAmount(RemainingAmount);
            loan.setStatus(LoanStatus.ACTIVE);

            setDate(loan,paymentSchedule);
            loan.setUsers(user);
            loansList.add(loan);
            user.setLoans(loansList);
            generatePaymentSchedule(loan);
            loan.setInstallmentAmount(monthlyInstallment(loan.getRemainingAmount(),loan.getMonths()));
            userRepository.save(user);
            return new ResponseEntity<>(loanRepository.save(loan),HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan is not Approved , Can't Sancation Loan");
        }
    }


    @Override
    public void setDate(Loan loan,PaymentSchedule paymentSchedule){
        Calendar calendar = Calendar.getInstance();
        Date presentDate = calendar.getTime();
        loan.setStartDate(presentDate );

        calendar.add(Calendar.MONTH, (int)loan.getMonths());
        Date EndDate = calendar.getTime();
        loan.setEndDate(EndDate);
        paymentSchedule.setDueDate(EndDate);
    }

    @Override
    public void setMonthlyInterest(Loan loan){
        double monthlyInterestRate;
        if(loan.getMonths()==3){
            monthlyInterestRate=2;
            loan.setInterestRate(monthlyInterestRate);
        }if(loan.getMonths()==6){
            monthlyInterestRate=2;
            loan.setInterestRate(monthlyInterestRate);
        }if(loan.getMonths()==2){
            monthlyInterestRate=2;
            loan.setInterestRate(monthlyInterestRate);
        }if(loan.getMonths()==12){
            monthlyInterestRate =2;
            loan.setInterestRate(monthlyInterestRate);
        }
    }

    @Override
    public void generatePaymentSchedule(Loan loan) {
        LocalDate startDate = loan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        IntStream.range(0, (int) loan.getMonths())
                .mapToObj(i -> {
                    PaymentSchedule paymentSchedule = new PaymentSchedule();
                    paymentSchedule.setPaymentScheduleId(generateNextId());
                    paymentSchedule.setLoan(loan);
                    paymentSchedule.setInstallmentAmount(monthlyInstallment(loan.getRemainingAmount(),loan.getMonths()));
                    paymentSchedule.setDueDate(Date.from(startDate.plusMonths(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    paymentScheduleRepository.save(paymentSchedule);
                    return paymentSchedule;
                })
                .collect(Collectors.toList());
    }


    @Override
    public Long generateNextId() {
        return psId++;
    }


    @Override
    public double monthlyInstallment(double totalAmount, double months) {
        double monthlyInstallment = totalAmount / months;
        return monthlyInstallment;
    }

    @Override
    public double remainingAmount(double LoanAmount,double rate,double months){
        double totalAmount = LoanAmount * Math.pow(1 + (rate/100), months);
        System.out.println(totalAmount+ " "+rate+" "+ months+" "+LoanAmount);
        return totalAmount;
    }



}
