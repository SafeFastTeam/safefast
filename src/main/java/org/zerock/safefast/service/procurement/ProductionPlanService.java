package org.zerock.safefast.service.procurement;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.entity.ProductionPlanItem;
import org.zerock.safefast.repository.ProductionPlanItemRepository;
import org.zerock.safefast.repository.ProductionPlanRepository;
import org.zerock.safefast.repository.ProcurementPlanRepository;

import java.util.List;

@Service
public class ProductionPlanService {

    private final ProductionPlanRepository productionPlanRepository;
    private final ProductionPlanItemRepository productionPlanItemRepository;

    public ProductionPlanService(ProductionPlanRepository productionPlanRepository, ProductionPlanItemRepository productionPlanItemRepository) {
        this.productionPlanRepository = productionPlanRepository;
        this.productionPlanItemRepository = productionPlanItemRepository;
    }

    @Transactional
    public ProductionPlan saveProductionPlan(ProductionPlan productionPlan, List<ProductionPlanItem> productionPlanItems) {
        ProductionPlan savedPlan = productionPlanRepository.save(productionPlan);
        for (ProductionPlanItem item : productionPlanItems) {
            item.setProductionPlan(savedPlan);
            productionPlanItemRepository.save(item);
        }
        return savedPlan;
    }

    public List<ProductionPlan> getAllProductionPlans() {
        return productionPlanRepository.findAll();
    }

    public List<ProductionPlan> findAll() {
        return productionPlanRepository.findAll();

    }
}
