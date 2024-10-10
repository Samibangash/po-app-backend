package com.poapp.repository;

import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    
//     // Add this method to enable the query by PurchaseOrderId
//     List<ApprovalWorkflow> findAllByPurchaseOrderId(Long purchaseOrderId);
// }

public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    // Custom query method to find an existing workflow
    ApprovalWorkflow findByPurchaseOrderAndUserAndApprovalLevel(PurchaseOrder po, User user, Integer approvalLevel);

    // Other repository methods
    List<ApprovalWorkflow> findAllByPurchaseOrderId(Long poId);
}


