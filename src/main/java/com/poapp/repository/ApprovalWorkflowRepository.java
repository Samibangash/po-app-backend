package com.poapp.repository;

import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

// @Repository
// public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
//     ApprovalWorkflow findByPurchaseOrderAndUserAndApprovalLevel(PurchaseOrder po, User user, Integer approvalLevel);

//     List<ApprovalWorkflow> findAllByPurchaseOrderId(Long poId);

//     List<ApprovalWorkflow> findAllByUserId(Long userId);
// }

@Repository
public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    
    ApprovalWorkflow findByPurchaseOrderAndUserAndApprovalLevel(PurchaseOrder po, User user, Integer approvalLevel);

    List<ApprovalWorkflow> findAllByPurchaseOrderId(Long poId);

    List<ApprovalWorkflow> findAllByUserId(Long userId);
    
    // Custom query to fetch workflows for a user where the previous approval level is "Approved"
    @Query("SELECT aw FROM ApprovalWorkflow aw WHERE aw.user.id = :userId AND aw.approvalLevel = :approvalLevel " +
           "AND (SELECT awPrev.status FROM ApprovalWorkflow awPrev WHERE awPrev.purchaseOrder = aw.purchaseOrder " +
           "AND awPrev.approvalLevel = :previousApprovalLevel) = 'Approved'")
    List<ApprovalWorkflow> findByUserIdAndApprovalLevelWithPreviousLevelApproved(@Param("userId") Long userId, 
                                                                                 @Param("approvalLevel") Integer approvalLevel, 
                                                                                 @Param("previousApprovalLevel") Integer previousApprovalLevel);
}




