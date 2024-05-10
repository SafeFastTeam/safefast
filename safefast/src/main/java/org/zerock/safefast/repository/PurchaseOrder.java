package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrder extends JpaRepository<PurchaseOrder, String> {
}
