package org.zerock.safefast.controller.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.service.procurement.ProductionPlanService;


import java.util.List;

@Controller
@RequestMapping("/production")
public class ProductionPlanController {

    private final ProductionPlanService productionPlanService;

    @Autowired
    public ProductionPlanController(ProductionPlanService productionPlanService) {
        this.productionPlanService = productionPlanService;
    }

    @GetMapping("/production_plan")
    public String showProductionPlan(Model model) {
        List<ProductionPlan> productionPlans = productionPlanService.getAllProductionPlans();
        model.addAttribute("productionPlans", productionPlans);
        return "procurement/procurement"; // 실제 뷰의 이름으로 수정 필요
    }

    // 다른 요청 처리 메서드들 추가 가능
}