package org.zerock.safefast.controller.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.ProductionPlan;
import org.zerock.safefast.repository.ProductionPlanRepository;

import org.zerock.safefast.service.procurement.ProcurementPlanService;
import org.zerock.safefast.service.procurement.ProductionPlanService;
import org.zerock.safefast.service.production_plan.ProductService;


import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/procurement")
public class ProcurementPlanController {

    private final ProductionPlanService productionPlanService;
    private final ProcurementPlanService procurementPlanService;
    private final ProductService productService;

    @Autowired
    public ProcurementPlanController(ProductionPlanService productionPlanService, ProcurementPlanService procurementPlanService, ProductService productService) {
        this.productionPlanService = productionPlanService;
        this.procurementPlanService = procurementPlanService;
        this.productService = productService;
    }

    // 조달 계획 제출 폼 보여주기
    @GetMapping("/procurement")
    public String showProcurementPlans(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<ProductionPlan> productionPlanPage = productService.findAll(pageable);
        model.addAttribute("productionPlans", productionPlanPage.getContent());
        model.addAttribute("totalPages", productionPlanPage.getTotalPages());
        model.addAttribute("currentPage", productionPlanPage.getNumber());
        model.addAttribute("pageSize", productionPlanPage.getSize());
        return "/procurement/procurement";
    }

    // 등록 버튼을 눌렀을 때의 요청을 처리하는 메서드
    @PostMapping("/submit_procurement_plan")
    public String submitProcurementPlan(ProcurementPlan procurementPlan, @RequestParam String businessNumber) {
        System.out.println("submitProcurementPlan 메소드 호출됨");

        try {
            procurementPlan.setProcProgress(0);
            procurementPlan.setBusinessNumber(businessNumber);
            procurementPlanService.saveProcurementPlan(procurementPlan);

            // 로그로 필드 값 확인
            System.out.println("procPlanNumber: " + procurementPlan.getProcPlanNumber());
            System.out.println("procQuantity: " + procurementPlan.getProcQuantity());
            System.out.println("procDueDate: " + procurementPlan.getProcDuedate());
            System.out.println("procProgress: " + procurementPlan.getProcProgress());
            System.out.println("itemCode: " + procurementPlan.getItemCode());
            System.out.println("productCode: " + procurementPlan.getProductCode());
            System.out.println("businessNumber: " + procurementPlan.getBusinessNumber());

        } catch (Exception e) {
            e.printStackTrace();
            return "error/500"; // 적절한 에러 페이지로 리디렉션
        }
        return "redirect:/procurement/procurement";
    }

    @Autowired
    ProcurementPlanService procurementService;

    @GetMapping("/getPlan")
    public ResponseEntity<Map<String, Object>> getProcurementPlan(@RequestParam String procPlanNumber) {
        Optional<ProcurementPlan> planOptional = procurementPlanService.getProcurementPlanByNumber(procPlanNumber);
        if (planOptional.isPresent()) {
            ProcurementPlan plan = planOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("procQuantity", plan.getProcQuantity());
            response.put("procDuedate", plan.getProcDuedate());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
