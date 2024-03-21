package com.example.LoanManagementSystem.models;

import jakarta.persistence.Embeddable;

public enum LoanStatus {
    ACTIVE,
    PAID,
    DEFAULTED,
    CLOSED
}
