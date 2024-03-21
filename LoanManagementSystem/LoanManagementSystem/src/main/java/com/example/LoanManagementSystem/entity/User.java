package com.example.LoanManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    private Long userId;

    private String name;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;

    private String role;
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Loan> loans=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
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
