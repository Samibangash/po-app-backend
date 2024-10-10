package com.poapp.controller;

import com.poapp.common.ApiResponse;
import com.poapp.dto.PurchaseOrderDTO;
import org.springframework.http.ResponseEntity;
import com.poapp.mapper.DTOMapper;
import com.poapp.model.PurchaseOrder;
import com.poapp.service.PurchaseOrderService;
import java.util.List;  // Correct import for List
import java.util.stream.Collectors;  // For stream operations
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    @Autowired
    private DTOMapper dtoMapper; 

    @Autowired
    private PurchaseOrderService poService;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) {
    // Create and save the purchase order
    PurchaseOrder po = dtoMapper.toPurchaseOrderEntity(poDTO);
    PurchaseOrder createdPO = poService.createPurchaseOrder(po);
    PurchaseOrderDTO createdPODTO = dtoMapper.toPurchaseOrderDTO(createdPO);
    

            ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
                "Success", 
                HttpStatus.OK.value(), 
                createdPODTO, 
                true
            );

            return ResponseEntity.ok(response);

    }

    @PutMapping(value = "/{id}/status", consumes = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        PurchaseOrderDTO updatedPODTO = dtoMapper.toPurchaseOrderDTO(updatedPO);
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            updatedPODTO, 
            true
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> getPurchaseOrderById(@PathVariable Long id) {
        Optional<PurchaseOrder> poOptional = Optional.ofNullable(poService.getPurchaseOrderById(id));

        if (!poOptional.isPresent()) {
            ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
                "Purchase Order not found", 
                HttpStatus.NOT_FOUND.value(), 
                null, 
                false
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        PurchaseOrderDTO poDTO = dtoMapper.toPurchaseOrderDTO(poOptional.get());
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            poDTO, 
            true
        );

        return ResponseEntity.ok(response);
    }



    // @GetMapping(produces = "application/json")
    // public ResponseEntity<ApiResponse<List<PurchaseOrderDTO>>> getAllPurchaseOrders() {
    //     List<PurchaseOrder> poList = poService.getAllPurchaseOrders();
        
    //     if (poList.isEmpty()) {
    //         ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
    //             "No Purchase Orders found", 
    //             HttpStatus.NOT_FOUND.value(), 
    //             null, 
    //             false
    //         );
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    //     }
        
    //     List<PurchaseOrderDTO> poDTOList = poList.stream()
    //                                             .map(dtoMapper::toPurchaseOrderDTO)
    //                                             .collect(Collectors.toList());

    //     ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
    //         "Success", 
    //         HttpStatus.OK.value(), 
    //         poDTOList, 
    //         true
    //     );

    //     return ResponseEntity.ok(response);
    // }


    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse<List<PurchaseOrderDTO>>> getPurchaseOrdersByStatus(
        @RequestParam(value = "status", required = false) String status) {
        
        List<PurchaseOrder> poList;

        if (status != null && !status.isEmpty()) {
            // Fetch purchase orders by status if provided
            poList = poService.getPurchaseOrdersByStatus(status);
        } else {
            // Fetch all purchase orders if no status is provided
            poList = poService.getAllPurchaseOrders();
        }

        if (poList.isEmpty()) {
            ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
                "No Purchase Orders found", 
                HttpStatus.NOT_FOUND.value(), 
                null, 
                false
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        List<PurchaseOrderDTO> poDTOList = poList.stream()
                                                .map(dtoMapper::toPurchaseOrderDTO)
                                                .collect(Collectors.toList());

        ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            poDTOList, 
            true
        );

        return ResponseEntity.ok(response);
    }


    


}
