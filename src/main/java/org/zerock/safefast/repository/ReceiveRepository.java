package org.zerock.safefast.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.Receive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiveRepository extends JpaRepository<Receive, Integer> {

    @Query("SELECT SUM(r.receiveQuantity) FROM Receive r WHERE r.item = :itemCode")
    Integer sumQuantityByItem(Item item);

    @Query("SELECT COUNT(r) FROM Receive r WHERE r.receiveDate BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Receive r WHERE r.receiveDate BETWEEN :startDate AND :endDate")
    List<Receive> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
