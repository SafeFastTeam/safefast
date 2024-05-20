package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.ProcurementPlan;

import java.util.Optional;

@Repository
public interface ProcurementPlanRepository extends JpaRepository<ProcurementPlan, String> {
    Optional<ProcurementPlan> findByProcPlanNumber(String procPlanNumber);
}
