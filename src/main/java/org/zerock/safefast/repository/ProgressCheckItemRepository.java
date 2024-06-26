package org.zerock.safefast.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.safefast.entity.ProgressCheckItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProgressCheckItemRepository extends JpaRepository<ProgressCheckItem, Integer> {

    @Query("SELECT p FROM ProgressCheckItem p WHERE p.purchaseOrder.purchOrderNumber = :purchOrderNumber")
    List<ProgressCheckItem> findByPurchOrderNumber(String purchOrderNumber);

    @Query("SELECT p FROM ProgressCheckItem p WHERE p.purchaseOrder.purchOrderNumber = :purchOrderNumber AND p.progCheckOrder = :progCheckOrder")
    Optional<ProgressCheckItem> findByPurchOrderNumberAndProgCheckOrder(String purchOrderNumber, int progCheckOrder);


    @Query("SELECT COUNT(DISTINCT p.progCheckResult) FROM ProgressCheckItem p WHERE p.progCheckResult = '1' AND p.purchaseOrder.purchOrderNumber = :purchOrderNumber AND p.progCheckDate BETWEEN :startDate AND :endDate")
    long countCompletedProgressChecksByDateRange(@Param("purchOrderNumber") String purchOrderNumber, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
