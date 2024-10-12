package com.poapp.repository;

import com.poapp.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;  // Correct import
import java.util.Optional; // Correct import for Optional
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByStatus(String status);

    @Query("SELECT po.po_number FROM PurchaseOrder po ORDER BY po.id DESC")
    List<String> findLatestPoNumber(Pageable pageable);  // Using Pageable to limit results

    List<PurchaseOrder> findByUserId(Long userId);


}
