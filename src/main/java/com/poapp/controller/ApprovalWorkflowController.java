package com.poapp.controller;

import com.poapp.model.ApprovalWorkflow;
import com.poapp.service.ApprovalWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow")
public class ApprovalWorkflowController {

    @Autowired
    private ApprovalWorkflowService workflowService;

 

    @PostMapping("/create")
    public ResponseEntity<ApprovalWorkflow> createWorkflow(@RequestParam Long poId, @RequestParam Long userId, @RequestParam Integer approvalLevel) {
        ApprovalWorkflow workflow = workflowService.firstOrCreateApprovalWorkflow(poId, userId, approvalLevel);
        return ResponseEntity.ok(workflow);
    }


    @PutMapping("/{workflowId}/approve")
    public ResponseEntity<ApprovalWorkflow> approveWorkflow(@PathVariable Long workflowId) {
        System.out.println("Creating PO: " + workflowId);
        ApprovalWorkflow workflow = workflowService.approveWorkflow(workflowId);
        return ResponseEntity.ok(workflow);
    }
    

    @PutMapping("/{workflowId}/reject")
    public ResponseEntity<ApprovalWorkflow> rejectWorkflow(@PathVariable Long workflowId) {
        ApprovalWorkflow workflow = workflowService.rejectWorkflow(workflowId);
        return ResponseEntity.ok(workflow);
    }

    @GetMapping("/po/{poId}")
    public ResponseEntity<List<ApprovalWorkflow>> getWorkflowsByPoId(@PathVariable Long poId) {
        List<ApprovalWorkflow> workflows = workflowService.getApprovalWorkflowsByPoId(poId);
        return ResponseEntity.ok(workflows);
    }
}
