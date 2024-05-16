package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.ClosedProcPlan;

@Repository
public interface ClosedProcPlanRepository extends JpaRepository<ClosedProcPlan, String> {

}
