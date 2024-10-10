package com.poapp.controller;

import com.poapp.dto.PurchaseOrderDTO;
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
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) {
        PurchaseOrder po = dtoMapper.toPurchaseOrderEntity(poDTO);
        PurchaseOrder createdPO = poService.createPurchaseOrder(po);
        PurchaseOrderDTO createdPODTO = dtoMapper.toPurchaseOrderDTO(createdPO);
        return new ResponseEntity<>(createdPODTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        return ResponseEntity.ok(updatedPO);
    }
}
