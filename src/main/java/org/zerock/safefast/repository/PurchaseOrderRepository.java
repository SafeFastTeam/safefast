package org.zerock.safefast.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {

    List<PurchaseOrder> findByPurchOrderNumberIn(List<String> orderNumbers);

    PurchaseOrder findByPurchOrderNumber(String purchOrderNumber);

//    그래프를 그리기 위해 갯수를 세는 메소드를 정의합니다.
@Query("SELECT COUNT(p) FROM PurchaseOrder p WHERE p.purchOrderDate BETWEEN :startDate AND :endDate")
long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}