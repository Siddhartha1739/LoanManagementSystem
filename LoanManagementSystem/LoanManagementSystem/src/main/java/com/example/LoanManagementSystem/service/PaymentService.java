package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.PaymentInterface;
import com.example.LoanManagementSystem.dao.LoanRepository;
import com.example.LoanManagementSystem.dao.PaymentRepository;
import com.example.LoanManagementSystem.dao.PaymentScheduleRepository;
import com.example.LoanManagementSystem.entity.*;
import com.example.LoanManagementSystem.models.LoanStatus;
import com.example.LoanManagementSystem.models.PaymentModel;
import com.example.LoanManagementSystem.models.PaymentScheduleModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
public class PaymentService implements PaymentInterface {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private Conversion conversion;



    @Override
    public String makePayment(PaymentModel paymentModel, Long loanId) {
       Long payId=paymentModel.getPaymentId();
        if(!paymentRepository.existsById(payId)) {
            Loan loan = loanRepository.getReferenceById(loanId);
            System.out.println(loan.getPaymentSchedule());
            PaymentSchedule paymentSchedule = loan.getPaymentSchedule().stream().filter(paymentSchedule1 -> conversion.DateToLocalDate(paymentSchedule1.getDueDate()).getMonth().equals(conversion.DateToLocalDate(paymentModel.getPaymentDate()).getMonth())).findFirst().orElse(null);
            if (paymentSchedule == null) {
                return "Payment Schedule is null";
            }
            if (loan.getRemainingAmount() <= 0) {
                closeLoan(loan);
                return "Your Loan is Cleared";
            }


            Payment payment = conversion.ModelToEntityPayment(paymentModel);


            if (payment.getPaymentDate().before(loan.getStartDate()) || payment.getPaymentDate().after(loan.getEndDate())) {
                return "Please Check the Date and Make Payment!!!";
            }

            payment.setLoan(loan);
            List<Payment> paymentList = loan.getPayments();
            List<PaymentSchedule> paymentScheduleList = loan.getPaymentSchedule();


            double installment = calculateMonthlyInstallment(loan.getRemainingAmount(), loan.getMonths());

            paymentList.add(payment);

            loan.setPayments(paymentList);

            double Amount = loan.getRemainingAmount();
            double apr = loan.getInterestRate() / 100;

            calculateLoan(Amount, apr, loan.getMonths(), loan, payment, paymentSchedule, installment);


            paymentScheduleList.add(paymentSchedule);
            loan.setPaymentSchedule(paymentScheduleList);

            paymentScheduleRepository.save(paymentSchedule);
            loanRepository.save(loan);
            paymentRepository.save(payment);
            return "Payment Successfull";
        }
        return "Payment Id already exist";

    }



    @Override
    public ResponseEntity<?> getPaymentById(Long paymentId) {
        Optional<Payment> payment=paymentRepository.findById(paymentId);
        if(payment.isPresent()){
            PaymentModel paymentModel=conversion.EntityToModelPayment(paymentRepository.findById(paymentId).orElse(null));
            return new ResponseEntity<>(paymentModel, HttpStatus.FOUND);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Details not found");
    }



    @Override
    public double calculateMonthlyInstallment(double totalAmount, double months) {
        double monthlyInstallment = totalAmount / months;
        return monthlyInstallment;
    }

    @Override
    public void closeLoan(Loan loan){
        loan.setStatus(LoanStatus.PAID);

        User user=loan.getUsers();
        List<LoanApplication> loanApplicationList=user.getLoanApplications();

        loanApplicationList.forEach(loanApplication -> {
            if (loanApplication.getLoanApplicationId().equals(loan.getLoanId())) {
                loanApplication.setStatus(LoanStatus.CLOSED);
            }
        });

    }


    @Override
    public   void   calculateLoan(double amount, double apr, double months,Loan loan,Payment payment,PaymentSchedule paymentSchedule,double installment) {
        if(amount>0) {

            LocalDate localDate1=conversion.DateToLocalDate(payment.getPaymentDate());
            int PaymentMonth = localDate1.getMonthValue();


            System.out.printf("Monthly Payment: $%.2f%n", installment);

            loan.getPaymentSchedule().forEach(paymentSchedule1->{
                Date PaymentScheduleDate = paymentSchedule1.getDueDate();
                LocalDate localDate2=conversion.DateToLocalDate(PaymentScheduleDate);
                int PaymentScheduleMonth = localDate2.getMonthValue();
                if(payment.getAmount()>=paymentSchedule1.getInstallmentAmount() && PaymentMonth==PaymentScheduleMonth){
                    paymentSchedule.setPaid(true);
                }
            });
            
            if(payment.getAmount()<installment){
                double Dueamount=installment-payment.getAmount();
                Dueamount/=2;
                amount+=Dueamount;
            }

            double remainingBalance = amount;
            remainingBalance -= payment.getAmount() ;
            remainingBalance=skipMonthPenaulty(loan,payment,remainingBalance);


            loan.setRemainingAmount(remainingBalance);

            if(remainingBalance<0){
                loan.setRemainingAmount(0);
                double amt=payment.getAmount()+remainingBalance;
                payment.setAmount(amt);
            }


            if( remainingBalance<=0) {
                closeLoan(loan);
            }

            if(payment.getPaymentDate().after(loan.getEndDate()) || (remainingBalance>0 && conversion.DateToLocalDate(payment.getPaymentDate()).getMonth().equals(conversion.DateToLocalDate(loan.getEndDate())))){
                loan.setStatus(LoanStatus.DEFAULTED);
            }


        }else{
            closeLoan(loan);
        }
    }

    @Override
    public double skipMonthPenaulty(Loan loan,Payment payment,double amount){
        AtomicBoolean shouldContinue = new AtomicBoolean(true);
        int penalty = loan.getPaymentSchedule().stream()
                .peek(paymentSchedule -> {
                    if (!shouldContinue.get()) {
                        return;
                    }
                    System.out.println(
                            conversion.DateToLocalDate(paymentSchedule.getDueDate()).getMonth() + "  <-> " +
                                    conversion.DateToLocalDate(payment.getPaymentDate()).getMonth() + " <-> " +
                                    paymentSchedule.isPaid());
                })
                .takeWhile(paymentSchedule -> {
                    if (conversion.DateToLocalDate(paymentSchedule.getDueDate()).getMonth().equals(conversion.DateToLocalDate(payment.getPaymentDate()).getMonth())) {
                        shouldContinue.set(false);
                        return false;
                    }
                    return true;
                })
                .filter(paymentSchedule -> !conversion.DateToLocalDate(paymentSchedule.getDueDate()).getMonth().equals(conversion.DateToLocalDate(payment.getPaymentDate()).getMonth()))
                .filter(paymentSchedule -> !paymentSchedule.isPaid())
                .mapToInt(paymentSchedule -> 1)
                .sum();

        amount+=penalty* (loan.getInstallmentAmount()/2);
        System.out.println("penalty =="+penalty +" "+amount);
        return amount;
    }
}