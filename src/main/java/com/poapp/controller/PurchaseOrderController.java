package com.poapp.controller;

import com.poapp.common.ApiResponse;
import com.poapp.dto.PurchaseOrderDTO;
import org.springframework.http.ResponseEntity;
import com.poapp.mapper.DTOMapper;
import com.poapp.model.PurchaseOrder;
import com.poapp.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   
   // Return the success response
   // Assuming PurchaseOrderDTO is the DTO object for PurchaseOrder
// @ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
//     "Success",                    // Response status
//     HttpStatus.OK.value(),         // HTTP status code
//     createdPODTO,                  // Data (created PurchaseOrderDTO)
//     true                           // Success flag
// );
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            createdPODTO, 
            true
        );

        return ResponseEntity.ok(response);

}

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        return ResponseEntity.ok(updatedPO);
    }
}
