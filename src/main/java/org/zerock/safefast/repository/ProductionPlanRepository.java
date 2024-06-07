package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.safefast.entity.ProductionPlan;

public interface ProductionPlanRepository extends JpaRepository<ProductionPlan, String> {

    @Query("SELECT MAX(p.prodPlanCode) FROM ProductionPlan p")
    String findLastProdPlanCode();
}
