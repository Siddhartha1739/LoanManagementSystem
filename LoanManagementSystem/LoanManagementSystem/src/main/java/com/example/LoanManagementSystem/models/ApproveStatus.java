package com.example.LoanManagementSystem.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

public enum ApproveStatus {
    PENDING,
    APPROVED,
    REJECTED
}
