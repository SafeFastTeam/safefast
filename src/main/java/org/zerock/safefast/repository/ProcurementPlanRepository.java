package org.zerock.safefast.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.ProcurementPlan;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProcurementPlanRepository extends JpaRepository<ProcurementPlan, String> {
    Optional<ProcurementPlan> findByProcPlanNumber(String procPlanNumber);

    //    그래프를 그리기 위해 갯수를 세는 메소드를 정의합니다.
    @Query("SELECT COUNT(p) FROM ProcurementPlan p WHERE p.procDuedate BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
