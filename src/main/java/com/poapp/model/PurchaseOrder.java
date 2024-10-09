package com.poapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal totalAmount;
    private String status; // e.g., Pending, Approved, Rejected

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<ApprovalWorkflow> approvalWorkflows;

    public void setStatus(String status) {
        this.status = status;
    }
}
