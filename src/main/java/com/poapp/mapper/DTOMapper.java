package com.poapp.mapper;

import org.springframework.stereotype.Component;

import com.poapp.dto.ApprovalWorkflowDTO;
import com.poapp.dto.PurchaseOrderDTO;
import com.poapp.dto.UserDTO;
import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    // Convert User entity to UserDTO
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());  // Add password mapping
        dto.setRole(user.getRole());
        return dto;
    }

    // Convert UserDTO to User entity
    public User toUserEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());  // Add password mapping
        user.setRole(userDTO.getRole());
        return user;
    }

    // Convert ApprovalWorkflow entity to ApprovalWorkflowDTO
    public ApprovalWorkflowDTO toApprovalWorkflowDTO(ApprovalWorkflow aw) {
        if (aw == null) return null;

        ApprovalWorkflowDTO dto = new ApprovalWorkflowDTO();
        dto.setId(aw.getId());
        dto.setApprovalLevel(aw.getApprovalLevel());
        dto.setStatus(aw.getStatus());
        dto.setUser(toUserDTO(aw.getUser())); // Map user to UserDTO
        return dto;
    }

    // Convert ApprovalWorkflowDTO to ApprovalWorkflow entity
    public ApprovalWorkflow toApprovalWorkflowEntity(ApprovalWorkflowDTO awDTO) {
        if (awDTO == null) return null;

        ApprovalWorkflow aw = new ApprovalWorkflow();
        aw.setId(awDTO.getId());
        aw.setApprovalLevel(awDTO.getApprovalLevel());
        aw.setStatus(awDTO.getStatus());
        aw.setUser(toUserEntity(awDTO.getUser())); // Map UserDTO back to User entity
        return aw;
    }
    // Convert PurchaseOrder entity to PurchaseOrderDTO
    public PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder po) {
        if (po == null) return null;

        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(po.getId());
        dto.setDescription(po.getDescription());
        dto.setTotalAmount(po.getTotalAmount());
        dto.setStatus(po.getStatus());
        
        // Convert List<ApprovalWorkflow> to List<ApprovalWorkflowDTO>
        if (po.getApprovalWorkflows() != null) {
            List<ApprovalWorkflowDTO> awDTOs = po.getApprovalWorkflows().stream()
                .map(this::toApprovalWorkflowDTO)
                .collect(Collectors.toList());
            dto.setApprovalWorkflows(awDTOs);
        }
        return dto;
    }

    // Convert PurchaseOrderDTO to PurchaseOrder entity
    public PurchaseOrder toPurchaseOrderEntity(PurchaseOrderDTO poDTO) {
        if (poDTO == null) return null;

        PurchaseOrder po = new PurchaseOrder();
        po.setId(poDTO.getId());
        po.setDescription(poDTO.getDescription());
        po.setTotalAmount(poDTO.getTotalAmount());
        po.setStatus(poDTO.getStatus());

        // Convert List<ApprovalWorkflowDTO> to List<ApprovalWorkflow>
        if (poDTO.getApprovalWorkflows() != null) {
            List<ApprovalWorkflow> awEntities = poDTO.getApprovalWorkflows().stream()
                .map(this::toApprovalWorkflowEntity)
                .collect(Collectors.toList());
            po.setApprovalWorkflows(awEntities);
        }
        return po;
    }
}
