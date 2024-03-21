package com.example.LoanManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Admin {
    @Id
    private Long adminId;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanApproval> loanApprovalList=new ArrayList<>();

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
