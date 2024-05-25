package org.zerock.safefast.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.safefast.entity.ProgressCheckItem;

import java.time.LocalDateTime;

public interface ProgressCheckItemRepository extends JpaRepository<ProgressCheckItem, Integer> {

    @Query("SELECT COUNT(p) FROM ProgressCheckItem p WHERE p.progCheckDate BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
