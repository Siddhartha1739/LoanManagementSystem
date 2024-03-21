package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.LoanApplication;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserModel {
    @NotNull(message = "User Id is Required")
    private Long userId;
    @NotBlank(message = "User name is Required")
    private String name;
    @NotBlank(message = "User Password is Required")
    private String password;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;
    @NotNull(message = "Phone Number is Required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    @NotBlank(message = "Address is Required")
    private String address;
    @NotBlank(message = "Role is Required")
    private String role;
    private List<Loan> loans=new ArrayList<>();
    private List<LoanApplication> loanApplications=new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
