package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentModel {
    private Long paymentId;
    private Loan loan;
    @NotNull(message = "Payment Date is Required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d",lenient = OptBoolean.FALSE)
    private Date paymentDate;
    @NotNull(message = " Amount is Required")
    private double amount;

    @Override
    public String toString() {
        return "PaymentModel{" +
                "paymentId=" + paymentId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}
