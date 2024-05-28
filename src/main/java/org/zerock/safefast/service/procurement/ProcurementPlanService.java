package org.zerock.safefast.service.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.repository.ProcurementPlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProcurementPlanService {

    private final ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    public ProcurementPlanService(ProcurementPlanRepository procurementPlanRepository) {
        this.procurementPlanRepository = procurementPlanRepository;
    }

    public List<ProcurementPlan> getAllProcurementPlans() {
        return procurementPlanRepository.findAll();
    }



    @Transactional
    public void saveProcurementPlan(ProcurementPlan procurementPlan) {
        procurementPlan.setProcPlanNumber(generateNextProcPlanNumber());
        procurementPlanRepository.save(procurementPlan);
    }

    public Optional<ProcurementPlan> getProcurementPlanByNumber(String procPlanNumber) {
        return procurementPlanRepository.findById(procPlanNumber);
    }

    private String generateNextProcPlanNumber() {
        String maxProcPlanNumber = procurementPlanRepository.findMaxProcPlanNumber();
        if (maxProcPlanNumber == null) {
            return "PROC-001";
        }

        try {
            String[] parts = maxProcPlanNumber.split("-");
            int nextNumber = Integer.parseInt(parts[1]) + 1;
            return String.format("PROC-%03d", nextNumber);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // 예외 발생 시 기본값 반환
            return "PROC-001";
        }
    }
}