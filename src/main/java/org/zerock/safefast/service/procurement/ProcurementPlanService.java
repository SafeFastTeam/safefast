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
        System.out.println("저장하기 전: " + procurementPlan.getProcDuedate());
        procurementPlanRepository.save(procurementPlan);
        System.out.println("저장 완료");
    }

    public List<ProcurementPlan> getAllProcurementPlans() {

        return procurementPlanRepository.findAll();
    }

}
