package com.poapp.service;

import com.poapp.model.Item;
import com.poapp.model.PurchaseOrder;
import com.poapp.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository poRepository;
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po, List<Item> items) {
        List<String> latestPoNumbers = poRepository.findLatestPoNumber(PageRequest.of(0, 1));
String latestPoNumber = latestPoNumbers.isEmpty() ? null : latestPoNumbers.get(0);
        String newPoNumber = generateNextPoNumber(latestPoNumber);
        po.setPoNumber(newPoNumber);
        for (Item item : items) {
            item.setPurchaseOrder(po);
        }
        po.setItems(items);

        return poRepository.save(po);
    }
    private String generateNextPoNumber(String latestPoNumber) {
        if (latestPoNumber == null || latestPoNumber.isEmpty()) {
            return "PO-1000";
        } else {
            String[] parts = latestPoNumber.split("-");
            int number = Integer.parseInt(parts[1]);
            return "PO-" + (number + 1);
        }
    }

    public PurchaseOrder getPurchaseOrderById(Long poId) {
        return poRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("PO not found"));
    }

    public PurchaseOrder updatePOStatus(Long poId, String status) {
        PurchaseOrder po = getPurchaseOrderById(poId);
        po.setStatus(status);
        return poRepository.save(po);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return poRepository.findAll();
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return poRepository.findByStatus(status);
    }
}
