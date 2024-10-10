package com.poapp.repository;

import com.poapp.model.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByStatus(String status);
}

