
import java.util.stream.IntStream;

public class sam2 {
        public static void main(String[] args) {
            calculateRemainingBalance(5000, 10, 6, 843.75); // Scenario 1: Payment of $843.75
            calculateRemainingBalance(5000, 10, 6, 700);    // Scenario 2: Payment of $700
            calculateRemainingBalance(5000, 10, 6, 1000);   // Scenario 3: Payment of $1000
        }

        public static void calculateRemainingBalance(double loanAmount, double apr, int months, double payment) {
            final double[] remainingPrincipal = {loanAmount};
            double monthlyInterestRate = apr / 100 / 12;

            IntStream.rangeClosed(1, months)
                    .takeWhile(i -> remainingPrincipal[0] > 0)
                    .forEach(i -> {
                        double interestForMonth = remainingPrincipal[0] * monthlyInterestRate;
                        double principalPaid = Math.min(payment - interestForMonth, remainingPrincipal[0]);
                        remainingPrincipal[0] -= principalPaid;
                        System.out.println("Month " + i + ": Remaining Balance = $" + String.format("%.2f", remainingPrincipal[0]));
                    });
            System.out.println();
        }
        /*if (payment.getPaymentDate().after(loan.getStartDate())) {
            System.out.println("Entered payment Delay method");
            paymentList.forEach(payment1 -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(loan.getStartDate());
                calendar.add(Calendar.MONTH, 1); // Start from the next month
                Date startDate = calendar.getTime();
                calendar.setTime(payment1.getPaymentDate());
                Date paymentDate = calendar.getTime();
                long months = ChronoUnit.MONTHS.between(
                        Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1),
                        Instant.ofEpochMilli(paymentDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1)
                );
                for (int i = 1; i < months; i++) {
                    calendar.setTime(startDate);
                    calendar.add(Calendar.MONTH, i);
                    Date missingMonth = calendar.getTime();
                    boolean hasPayment = paymentList.stream()
                            .anyMatch(p -> {
                                calendar.setTime(p.getPaymentDate());
                                return calendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                                        calendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH);
                            });
                    if (!hasPayment) {
                        payment1.setPaymentDate(null);
                        payment1.setAmount(0);
                        payment1.setPaymentId(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
                        paymentList.add(payment1);
                    }
                }
            });
        }*/
}
