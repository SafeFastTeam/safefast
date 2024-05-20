package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
    PurchaseOrder findByProcPlanNumber(String procPlanNumber);

    List<PurchaseOrder> findByPurchOrderNumberIn(List<String> orderNumbers);
}