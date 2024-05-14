package org.zerock.safefast.controller.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.service.procurement.ProcurementPlanService;
import org.zerock.safefast.service.procurement.ProductionPlanService;

import java.util.List;

@Controller
@RequestMapping("/procurement")
public class ProcurementPlanController {

    private final ProductionPlanService productionPlanService;
    private final ProcurementPlanService procurementPlanService;

    @Autowired
    public ProcurementPlanController(ProductionPlanService productionPlanService, ProcurementPlanService procurementPlanService) {
        this.productionPlanService = productionPlanService;
        this.procurementPlanService = procurementPlanService;
    }

    @GetMapping("/production_plan")
    public String showProductionPlan(Model model) {
        List<ProductionPlan> productionPlans = productionPlanService.getAllProductionPlans();
        model.addAttribute("productionPlans", productionPlans);
        return "procurement/procurement";
    }

    // 등록 버튼을 눌렀을 때의 요청을 처리하는 메서드
    @PostMapping("/submit_production_plan")
    public String submitProductionPlan(ProductionPlan productionPlan) {
        productionPlanService.saveProductionPlan(productionPlan);
        return "redirect:/procurement/production_plan";
    }

    // 조달 계획 제출 폼 보여주기
    @GetMapping("/procurement")
    public String showProcurement(Model model) {
        // ProcurementPlan과 관련된 로직 추가 가능
        return "procurement/procurement";
    }

    // 등록 버튼을 눌렀을 때의 요청을 처리하는 메서드
    @PostMapping("/submit_procurement_plan")
    public String submitProcurementPlan(ProcurementPlan procurementPlan) {
        procurementPlanService.saveProcurementPlan(procurementPlan);
        return "redirect:/procurement/procurement";
    }
}
