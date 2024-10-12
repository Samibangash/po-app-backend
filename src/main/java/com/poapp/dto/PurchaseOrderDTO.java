package com.poapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrderDTO {
    private Long id;
    private String description;
    private String po_number;
    private BigDecimal totalAmount;
    private String status;
    private UserDTO user;  
    private List<ItemDTO> items;
    // List of ApprovalWorkflowDTOs
    private List<ApprovalWorkflowDTO> approvalWorkflows;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setPoNumber(String po_number) {
        this.po_number = po_number;
    }
    public String getPoNumber() {
        return po_number;
    }

    public List<ApprovalWorkflowDTO> getApprovalWorkflows() {
        return approvalWorkflows;
    }

    public void setApprovalWorkflows(List<ApprovalWorkflowDTO> approvalWorkflows) {
        this.approvalWorkflows = approvalWorkflows;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
