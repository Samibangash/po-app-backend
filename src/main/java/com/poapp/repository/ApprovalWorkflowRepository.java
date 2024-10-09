package com.poapp.repository;

import com.poapp.model.ApprovalWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    
//     // Add this method to enable the query by PurchaseOrderId
//     List<ApprovalWorkflow> findAllByPurchaseOrderId(Long purchaseOrderId);
// }

public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    List<ApprovalWorkflow> findAllByPurchaseOrderId(Long purchaseOrderId);
}

