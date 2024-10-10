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

    // @Autowired
    // private ApprovalWorkflowRepository workflowRepository;

    // @Autowired
    // private PurchaseOrderRepository poRepository;

    // @Autowired
    // private UserRepository userRepository;

    // public ApprovalWorkflow createApprovalWorkflow(Long poId, Long userId, Integer approvalLevel) {
    //     PurchaseOrder po = poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
    //     User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    //     ApprovalWorkflow workflow = new ApprovalWorkflow();
    //     workflow.setPurchaseOrder(po);
    //     workflow.setUser(user);
    //     workflow.setApprovalLevel(approvalLevel);
    //     workflow.setStatus("Pending");


    //     return workflowRepository.save(workflow);
    // }

    @Autowired
    private ApprovalWorkflowRepository workflowRepository;

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private UserRepository userRepository;

    public ApprovalWorkflow firstOrCreateApprovalWorkflow(Long poId, Long userId, Integer approvalLevel) {
        PurchaseOrder po = poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the workflow already exists
        ApprovalWorkflow existingWorkflow = workflowRepository
            .findByPurchaseOrderAndUserAndApprovalLevel(po, user, approvalLevel);

        if (existingWorkflow != null) {
            // If workflow exists, update the status or any other fields if necessary
            existingWorkflow.setStatus("Pending"); // or any other status you want to reset to
            return workflowRepository.save(existingWorkflow);
        }

        // If workflow does not exist, create a new one
        ApprovalWorkflow newWorkflow = new ApprovalWorkflow();
        newWorkflow.setPurchaseOrder(po);
        newWorkflow.setUser(user);
        newWorkflow.setApprovalLevel(approvalLevel);
        newWorkflow.setStatus("Pending");

        return workflowRepository.save(newWorkflow);
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
