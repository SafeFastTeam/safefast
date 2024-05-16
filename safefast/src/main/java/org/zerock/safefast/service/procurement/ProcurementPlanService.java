package org.zerock.safefast.service.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.repository.ProcurementPlanRepository;

import java.util.List;

@Service
public class ProcurementPlanService {

    private final ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    public ProcurementPlanService(ProcurementPlanRepository procurementPlanRepository) {
        this.procurementPlanRepository = procurementPlanRepository;
    }

    public void saveProcurementPlan(ProcurementPlan procurementPlan) {
        // ProcurementPlan을 저장하는 메서드
        procurementPlanRepository.save(procurementPlan);
    }

    public List<ProcurementPlan> getAllProcurementPlans() {
        return procurementPlanRepository.findAll();
    }
}
