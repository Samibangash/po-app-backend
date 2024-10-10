// package com.poapp.service;

// import com.poapp.model.PurchaseOrder;
// import com.poapp.repository.PurchaseOrderRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class PurchaseOrderService {

//     @Autowired
//     private PurchaseOrderRepository poRepository;

//     public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
//         System.out.println("Creating PO: " + po);
//     return poRepository.save(po);
//     }

//     public PurchaseOrder updatePurchaseOrderStatus(Long poId, String status) {
//         PurchaseOrder po = poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
//         po.setStatus(status);
//         return poRepository.save(po);
//     }
// }




package com.poapp.service;

import com.poapp.model.PurchaseOrder;
import com.poapp.repository.PurchaseOrderRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;  // Correct import for List
import java.util.stream.Collectors;  // For stream operations


@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository poRepository;

    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        PurchaseOrder createdPO = poRepository.save(po);
        // Initialize relationships if necessary
        Hibernate.initialize(createdPO.getApprovalWorkflows());
        return createdPO;
    }

    public PurchaseOrder getPurchaseOrderById(Long poId) {
        return poRepository.findById(poId).orElseThrow(() -> new RuntimeException("PO not found"));
    }

    public PurchaseOrder updatePOStatus(Long poId, String status) {
        PurchaseOrder po = getPurchaseOrderById(poId);
        po.setStatus(status);
        return poRepository.save(po);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return poRepository.findAll();
    }

    // New method to get Purchase Orders by status
    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return poRepository.findByStatus(status);
    }
}

