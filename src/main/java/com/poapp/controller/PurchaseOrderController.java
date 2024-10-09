package com.poapp.controller;

import com.poapp.model.PurchaseOrder;
import com.poapp.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService poService;

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrder> createPO(@RequestBody PurchaseOrder po) {
        PurchaseOrder createdPO = poService.createPurchaseOrder(po);
        return ResponseEntity.ok(createdPO);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        return ResponseEntity.ok(updatedPO);
    }
}
