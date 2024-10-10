package com.poapp.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ApprovalWorkflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "po_id")
    private PurchaseOrder purchaseOrder;

    private Integer approvalLevel;
    private String status;

    // Setters and Getters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }



    // Getters
    public Integer getId() {
        return id;
    }

    public Integer getApprovalLevel() {
        return approvalLevel;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

}
