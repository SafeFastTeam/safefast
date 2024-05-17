package org.zerock.safefast.controller.purchase_order;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.service.procurement.ProcurementPlanService;

import java.util.List;


@Controller
@RequestMapping("/purchase_order")
public class PurchaseOrderController {

    @Autowired
    private ProcurementPlanService procurementPlanService;

    @GetMapping("/purchase_order")
    public String showPurchaseOrderPage(Model model) {
        List<ProcurementPlan> procurementPlans = procurementPlanService.getAllProcurementPlans();
        model.addAttribute("procurementPlans", procurementPlans);
        return "purchase_order/purchase_order";
    }
}
