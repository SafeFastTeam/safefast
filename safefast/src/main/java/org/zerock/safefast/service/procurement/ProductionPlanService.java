package org.zerock.safefast.service.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.repository.ProductionPlanRepository;
import org.zerock.safefast.repository.ProcurementPlanRepository;

import java.util.List;

@Service
public class ProductionPlanService {

    private final ProductionPlanRepository productionPlanRepository;

    @Autowired
    private ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    public ProductionPlanService(ProductionPlanRepository productionPlanRepository) {
        this.productionPlanRepository = productionPlanRepository;
    }

    public void saveProductionPlan(ProductionPlan productionPlan) {
        productionPlanRepository.save(productionPlan);
    }

    public List<ProductionPlan> getAllProductionPlans() {
        return productionPlanRepository.findAll();
    }

    public List<ProductionPlan> findAll() {
        return productionPlanRepository.findAll();

    }
}
