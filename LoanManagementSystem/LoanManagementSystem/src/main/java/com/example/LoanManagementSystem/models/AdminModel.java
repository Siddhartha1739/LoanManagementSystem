package com.example.LoanManagementSystem.models;

import com.example.LoanManagementSystem.entity.Loan;
import com.example.LoanManagementSystem.entity.LoanApproval;
import com.example.LoanManagementSystem.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminModel {
    @NotNull(message = "Admin Id is Required")
    private Long adminId;
    @NotBlank(message = "Admin Name is Required")
    private String name;
    @NotBlank(message = "Admin Password is Required")
    private String password;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;
    @NotNull(message = "Phone Number is Required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    @NotBlank(message = "Admin Role is Required")
    private String role;
    private List<LoanApproval> loanApproval=new ArrayList<>();

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
