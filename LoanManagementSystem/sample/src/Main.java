//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
   /* public static void calculateLoan(double amount, double apr, int months) {
            double monthlyRate = apr / 12.0;
            double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

            System.out.printf("Monthly Payment: $%.2f%n", monthlyPayment);

            double remainingBalance = amount;
            for (int i = 1; i <= months; i++) {
                double interest = remainingBalance * monthlyRate;
                double principal = monthlyPayment - interest;
                remainingBalance -= principal;

                System.out.printf("Month %d - Interest: $%.2f, Principal: $%.2f, Remaining Balance: $%.2f%n",
                        i, interest, principal, Math.max(remainingBalance, 0));
            }
        }

        public static void getNextMonthDetails(double remainingAmount, double apr) {
            double monthlyRate = apr / 12.0;
            double interest = remainingAmount * monthlyRate;
            double remainingBalance = remainingAmount - (remainingAmount - (remainingAmount * monthlyRate));

            System.out.printf("Next Month - Interest: $%.2f, Remaining Balance: $%.2f%n", interest, remainingBalance);
        }*/
        public static void main(String[] args) {
           /* double amount = 5000;
            double apr = 0.10;
            int months = 12;

            calculateLoan(amount, apr, months);

            double monthlyRate = apr / 12.0;
            double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

            getNextMonthDetails(amount - monthlyPayment, apr);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int month = localDate.getMonthValue();
            System.out.println(month);*/

            int payMonth=getPaymentMonth();
            int loanStartMonth=getloanStartMonth();
            int loanEndMonth=getloanEndMonth();

            if(payMonth<loanStartMonth && payMonth>loanEndMonth){
                System.out.println("Please Check the Date and Make Payment!!!");
            }else{
                System.out.println("Payment Successfull");
            }

        }
    private static int getPaymentMonth(){
        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.MARCH); // Note: Calendar.MONTH is zero-based
        calendar.set(Calendar.DAY_OF_MONTH, 13);

        int payMonth=calendar.get(Calendar.MONTH)+1;
        return payMonth;
    }
    private static int getloanStartMonth(){
        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.MARCH); // Note: Calendar.MONTH is zero-based
        calendar.set(Calendar.DAY_OF_MONTH, 13);

        int loanStartMonth=calendar.get(Calendar.MONTH)+1;
        return loanStartMonth;
    }
    private static int getloanEndMonth(){
        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, Calendar.MARCH); // Note: Calendar.MONTH is zero-based
        calendar.set(Calendar.DAY_OF_MONTH, 13);

        int loanEndMonth=calendar.get(Calendar.MONTH)+1;
        return loanEndMonth;
    }

   /* @Override
    public ResponseEntity<?> HighestPaid(){
        Optional<User> userWithLowestPaidLoan = userRepository.findAll().stream()
                .filter(user -> !user.getLoans().isEmpty())
                .min(Comparator.comparingDouble(user -> user.getLoans().stream()
                        .mapToDouble(Loan::getRemainingAmount).sum()));


        List<Loan> loanList=loanRepository.findAll();
        Loan lowestLoan=loanList.stream()
                .min(Comparator.comparingDouble(Loan::getRemainingAmount))
                .orElse(null);


        if (userWithLowestPaidLoan.isPresent()) {
            User user = userWithLowestPaidLoan.get();
            System.out.println("User with highest paid loan amount:" + user);
            Loan loan=user.getLoans().stream()
                    .max(Comparator.comparingDouble(Loan::getRemainingAmount))
                    .orElse(null);




            return new ResponseEntity<>(lowestLoan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }

    }*/


    }



