package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}