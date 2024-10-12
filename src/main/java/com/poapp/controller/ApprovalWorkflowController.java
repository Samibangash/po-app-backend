package com.poapp.controller;

import com.poapp.common.ApiResponse;
import com.poapp.dto.ApprovalWorkflowDTO;
import com.poapp.mapper.DTOMapper;
import com.poapp.model.ApprovalWorkflow;
import com.poapp.service.ApprovalWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;


import java.util.List;

@RestController
@RequestMapping("/api/workflow")
public class ApprovalWorkflowController {

    @Autowired
    private DTOMapper dtoMapper; 
    @Autowired
    private ApprovalWorkflowService workflowService;

 

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ApprovalWorkflow>> createWorkflow(@RequestParam Long poId, @RequestParam Long userId, @RequestParam Integer approvalLevel) {
        ApprovalWorkflow workflow = workflowService.firstOrCreateApprovalWorkflow(poId, userId, approvalLevel);
        // Create a success response with the ApprovalWorkflow object
        ApiResponse<ApprovalWorkflow> response = new ApiResponse<>(
                "Success", 
                HttpStatus.OK.value(), 
                workflow, 
                true
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{workflowId}/approve")
    public ResponseEntity<ApiResponse<ApprovalWorkflowDTO>> approveWorkflow(@PathVariable Long workflowId) {
        System.out.println("Creating PO: " + workflowId);
        ApprovalWorkflow workflow = workflowService.approveWorkflow(workflowId);
        
        ApprovalWorkflowDTO updatedDTO = dtoMapper.toApprovalWorkflowDTO(workflow);
        ApiResponse<ApprovalWorkflowDTO> response = new ApiResponse<>(
                "Success", 
                HttpStatus.OK.value(), 
                updatedDTO, 
                true
        );

        return ResponseEntity.ok(response);
    }
    

    @PutMapping("/{workflowId}/reject")
    public ResponseEntity<ApiResponse<ApprovalWorkflowDTO>> rejectWorkflow(@PathVariable Long workflowId) {
        ApprovalWorkflow workflow = workflowService.rejectWorkflow(workflowId);
        ApprovalWorkflowDTO updatedDTO = dtoMapper.toApprovalWorkflowDTO(workflow);
        ApiResponse<ApprovalWorkflowDTO> response = new ApiResponse<>(
                "Success", 
                HttpStatus.OK.value(), 
                updatedDTO, 
                true
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/po/{poId}")
    public ResponseEntity<ApiResponse<List<ApprovalWorkflow>>> getWorkflowsByPoId(@PathVariable Long poId) {
        List<ApprovalWorkflow> workflows = workflowService.getApprovalWorkflowsByPoId(poId);
        // ApprovalWorkflowDTO updatedDTO = dtoMapper.toApprovalWorkflowDTO(workflows);
        
        
        ApiResponse<List<ApprovalWorkflow>> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            workflows, 
            true
    );

    return ResponseEntity.ok(response);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<ApiResponse<List<ApprovalWorkflowDTO>>> getWorkflowsByUserId(@PathVariable("userID") Long userId) {
        List<ApprovalWorkflow> workflows = workflowService.getApprovalWorkflowsByUserId(userId);
        // ApprovalWorkflowDTO updatedDTO = dtoMapper.toApprovalWorkflowDTO(workflows);
        List<ApprovalWorkflowDTO> updatedDTOs = workflows.stream()
            .map(dtoMapper::toApprovalWorkflowDTO)
            .collect(Collectors.toList());

        ApiResponse<List<ApprovalWorkflowDTO>> response = new ApiResponse<>(
            "Success",
            HttpStatus.OK.value(),
            updatedDTOs,
            true
        );

        return ResponseEntity.ok(response);
    }


}
