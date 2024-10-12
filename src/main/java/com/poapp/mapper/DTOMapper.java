package com.poapp.mapper;

import org.springframework.stereotype.Component;

import com.poapp.dto.ApprovalWorkflowDTO;
import com.poapp.dto.PurchaseOrderDTO;
import com.poapp.dto.UserDTO;
import com.poapp.dto.ItemDTO;  // Import ItemDTO
import com.poapp.model.ApprovalWorkflow;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;
import com.poapp.model.Item;  // Import Item

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
        dto.setRoleName(user.getRoleName());
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
        user.setRoleName(userDTO.getRoleName());
        return user;
    }

    // Convert ApprovalWorkflow entity to ApprovalWorkflowDTO
    // public ApprovalWorkflowDTO toApprovalWorkflowDTO(ApprovalWorkflow aw) {
    //     if (aw == null) return null;

    //     ApprovalWorkflowDTO dto = new ApprovalWorkflowDTO();
    //     dto.setId(aw.getId());
    //     dto.setApprovalLevel(aw.getApprovalLevel());
    //     dto.setStatus(aw.getStatus());
    //     dto.setUser(toUserDTO(aw.getUser())); // Map user to UserDTO
    //     dto.setPurchaseOrder(toPurchaseOrderDTO(aw.getPurchaseOrder()));
    //     return dto;
    // }

    
    // Convert ApprovalWorkflow entity to ApprovalWorkflowDTO (Partial Mapping)
    public ApprovalWorkflowDTO toApprovalWorkflowDTO(ApprovalWorkflow aw) {
        if (aw == null) return null;

        ApprovalWorkflowDTO dto = new ApprovalWorkflowDTO();
        dto.setId(aw.getId());
        dto.setApprovalLevel(aw.getApprovalLevel());
        dto.setStatus(aw.getStatus());
        dto.setUser(toUserDTO(aw.getUser())); // Map user to UserDTO

        // Instead of mapping the full PurchaseOrderDTO, map only required fields to avoid cyclic dependency
        PurchaseOrderDTO poDTO = new PurchaseOrderDTO();
        poDTO.setId(aw.getPurchaseOrder().getId());
        poDTO.setDescription(aw.getPurchaseOrder().getDescription());
        dto.setPurchaseOrder(poDTO);

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
    //  public PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder po) {
    //     if (po == null) return null;

    //     PurchaseOrderDTO dto = new PurchaseOrderDTO();
    //     dto.setId(po.getId());
    //     dto.setDescription(po.getDescription());
    //     dto.setTotalAmount(po.getTotalAmount());
    //     dto.setPoNumber(po.getPoNumber());
    //     dto.setStatus(po.getStatus());

    //     // Convert List<Item> to List<ItemDTO>
    //     if (po.getItems() != null) {
    //         List<ItemDTO> itemDTOs = po.getItems().stream()
    //             .map(this::toItemDTO)
    //             .collect(Collectors.toList());
    //         dto.setItems(itemDTOs);
    //     }
    //     // Convert List<ApprovalWorkflow> to List<ApprovalWorkflowDTO>
    //     if (po.getApprovalWorkflows() != null) {
    //         List<ApprovalWorkflowDTO> awDTOs = po.getApprovalWorkflows().stream()
    //             .map(this::toApprovalWorkflowDTO)
    //             .collect(Collectors.toList());
    //         dto.setApprovalWorkflows(awDTOs);
    //     }


    //     return dto;
    // }


    // Convert PurchaseOrder entity to PurchaseOrderDTO (Partial Mapping)
    public PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder po) {
        if (po == null) return null;

        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(po.getId());
        dto.setDescription(po.getDescription());
        dto.setTotalAmount(po.getTotalAmount());
        dto.setPoNumber(po.getPoNumber());
        dto.setStatus(po.getStatus());

        // Convert List<Item> to List<ItemDTO>
        if (po.getItems() != null) {
            List<ItemDTO> itemDTOs = po.getItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }

        // Map only necessary fields for ApprovalWorkflows to avoid recursion
        if (po.getApprovalWorkflows() != null) {
            List<ApprovalWorkflowDTO> awDTOs = po.getApprovalWorkflows().stream()
                .map(aw -> {
                    ApprovalWorkflowDTO awDTO = new ApprovalWorkflowDTO();
                    awDTO.setId(aw.getId());
                    awDTO.setApprovalLevel(aw.getApprovalLevel());
                    awDTO.setStatus(aw.getStatus());
                    awDTO.setUser(toUserDTO(aw.getUser()));
                    return awDTO;
                })
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
        po.setPoNumber(poDTO.getPoNumber());
        po.setStatus(poDTO.getStatus());

        // Convert List<ItemDTO> to List<Item>
        if (poDTO.getItems() != null) {
            List<Item> items = poDTO.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());
            po.setItems(items);
        }

        // Convert List<ApprovalWorkflowDTO> to List<ApprovalWorkflow>
        if (poDTO.getApprovalWorkflows() != null) {
            List<ApprovalWorkflow> awEntities = poDTO.getApprovalWorkflows().stream()
                .map(this::toApprovalWorkflowEntity)
                .collect(Collectors.toList());
            po.setApprovalWorkflows(awEntities);
        }
        return po;
    }

    // Convert Item entity to ItemDTO
    public ItemDTO toItemDTO(Item item) {
        if (item == null) return null;

        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setItemName(item.getItemName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

    // Convert ItemDTO to Item entity
    public Item toItemEntity(ItemDTO itemDTO) {
        if (itemDTO == null) return null;

        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setItemName(itemDTO.getItemName());
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(itemDTO.getPrice());
        return item;
    }
    
}
