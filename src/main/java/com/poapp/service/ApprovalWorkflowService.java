package com.poapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;
import com.poapp.repository.ApprovalWorkflowRepository;
import com.poapp.repository.PurchaseOrderRepository;
import com.poapp.repository.UserRepository;
import java.util.stream.Collectors;
// import java.util.List;

@Service
public class ApprovalWorkflowService {

    @Autowired
    private ApprovalWorkflowRepository workflowRepository;

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private UserRepository userRepository;

    // Create or find an existing workflow
    public ApprovalWorkflow firstOrCreateApprovalWorkflow(Long poId, Long userId, Integer approvalLevel) {
        PurchaseOrder po = poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the workflow already exists
        ApprovalWorkflow existingWorkflow = workflowRepository.findByPurchaseOrderAndUserAndApprovalLevel(po, user, approvalLevel);

        if (existingWorkflow != null) {
            // If workflow exists, reset or update its status if necessary
            existingWorkflow.setStatus("Pending");
            return workflowRepository.save(existingWorkflow);
        }

        // Create a new workflow
        ApprovalWorkflow newWorkflow = new ApprovalWorkflow();
        newWorkflow.setPurchaseOrder(po);
        newWorkflow.setUser(user);
        newWorkflow.setApprovalLevel(approvalLevel);
        newWorkflow.setStatus("Pending");

        return workflowRepository.save(newWorkflow);
    }

    // Fetch all workflows for a given PO ID
    public List<ApprovalWorkflow> getApprovalWorkflowsByPoId(Long poId) {
        return workflowRepository.findAllByPurchaseOrderId(poId);
    }

    // Fetch all workflows for a specific user
    // public List<ApprovalWorkflow> getApprovalWorkflowsByUserId(Long userId) {
    //     return workflowRepository.findAllByUserId(userId);
    // }

    public List<ApprovalWorkflow> getApprovalWorkflowsByUserId(Long userId) {
        List<ApprovalWorkflow> workflows = workflowRepository.findAllByUserId(userId);
    
        // Filter workflows where the previous level is approved
       
        List<ApprovalWorkflow> filteredWorkflows = workflows.stream()
            .filter(workflow -> {
                int currentApprovalLevel = workflow.getApprovalLevel();
                if (currentApprovalLevel != 1) {
                    int previousApprovalLevel = currentApprovalLevel - 1;
    
                // Fetch workflows where the previous level is approved
                List<ApprovalWorkflow> approvedPreviousWorkflows = workflowRepository
                    .findByUserIdAndApprovalLevelWithPreviousLevelApproved(userId, currentApprovalLevel, previousApprovalLevel);
    
                return !approvedPreviousWorkflows.isEmpty();
                }

                return true; 
            })
            .collect(Collectors.toList());
    
        return filteredWorkflows;
    }
    
    

    // Approve the workflow (with previous level checks)
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

