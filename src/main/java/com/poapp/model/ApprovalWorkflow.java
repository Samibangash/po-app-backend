package com.poapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ApprovalWorkflow {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @ManyToOne
    // @JoinColumn(name = "purchase_order_id")
    // private PurchaseOrder purchaseOrder;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    // private Integer approvalLevel;
    // private String status; // e.g., Pending, Approved, Rejected

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "po_id", referencedColumnName = "id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Integer approvalLevel;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    // Corrected setUser method
    public void setUser(User user) {
        this.user = user;
    }

    // Corrected setPurchaseOrder method
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
