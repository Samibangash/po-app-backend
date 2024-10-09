package com.poapp.service;

import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.repository.ApprovalWorkflowRepository;
import com.poapp.repository.PurchaseOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poapp.model.User;
import com.poapp.repository.UserRepository;

import java.util.List;

@Service
public class ApprovalWorkflowService {

    @Autowired
    private ApprovalWorkflowRepository workflowRepository;

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private UserRepository userRepository;

    public ApprovalWorkflow createApprovalWorkflow(Long poId, Long userId, Integer approvalLevel) {
        PurchaseOrder po = poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ApprovalWorkflow workflow = new ApprovalWorkflow();
        workflow.setPurchaseOrder(po);
        workflow.setUser(user);
        workflow.setApprovalLevel(approvalLevel);
        workflow.setStatus("Pending");

        // ApprovalWorkflow workflow = new ApprovalWorkflow();
        // workflow.setPurchaseOrder(po);  // This will work now
        // workflow.setUser(user);          // This will work now
        // workflow.setApprovalLevel(approvalLevel);  // This will work now
        // workflow.setStatus("Pending");


        return workflowRepository.save(workflow);
    }

    public List<ApprovalWorkflow> getApprovalWorkflowsByPoId(Long poId) {
        return workflowRepository.findAllByPurchaseOrderId(poId);
    }

    public ApprovalWorkflow approveWorkflow(Long workflowId) {
        ApprovalWorkflow workflow = workflowRepository.findById(workflowId).orElseThrow(() -> new RuntimeException("Workflow not found"));
        workflow.setStatus("Approved");
        return workflowRepository.save(workflow);
    }

    public ApprovalWorkflow rejectWorkflow(Long workflowId) {
        ApprovalWorkflow workflow = workflowRepository.findById(workflowId).orElseThrow(() -> new RuntimeException("Workflow not found"));
        workflow.setStatus("Rejected");
        return workflowRepository.save(workflow);
    }
}
