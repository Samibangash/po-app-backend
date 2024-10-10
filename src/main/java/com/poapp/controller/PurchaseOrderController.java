package com.poapp.controller;

import com.poapp.dto.PurchaseOrderDTO;
import com.poapp.mapper.DTOMapper;
import com.poapp.model.PurchaseOrder;
import com.poapp.service.PurchaseOrderService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    // @Autowired
    // private PurchaseOrderService poService;

    // @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder po) {
    //     PurchaseOrder createdPO = poService.createPurchaseOrder(po);
    //     return new ResponseEntity<>(createdPO, HttpStatus.OK);
    // }

    @Autowired
    private DTOMapper dtoMapper;  // Autowire the DTOMapper

    @Autowired
    private PurchaseOrderService poService;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) {
        // Step 1: Convert DTO to Entity
        PurchaseOrder po = dtoMapper.toPurchaseOrderEntity(poDTO);
        
        // Step 2: Call the service to save the PurchaseOrder
        PurchaseOrder createdPO = poService.createPurchaseOrder(po);
        
        // Step 3: Convert the saved entity back to DTO
        PurchaseOrderDTO createdPODTO = dtoMapper.toPurchaseOrderDTO(createdPO);
        
        // Step 4: Return the saved DTO in the response
        return new ResponseEntity<>(createdPODTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        return ResponseEntity.ok(updatedPO);
    }
}
