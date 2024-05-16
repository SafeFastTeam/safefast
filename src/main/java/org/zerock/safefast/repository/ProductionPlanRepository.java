package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.ProductionPlan;

public interface ProductionPlanRepository extends JpaRepository<ProductionPlan, String> {

}
