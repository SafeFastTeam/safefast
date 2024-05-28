package org.zerock.safefast.controller.purchase_order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderRequest;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.repository.ProcurementPlanRepository;
import org.zerock.safefast.repository.PurchaseOrderRepository;
import org.zerock.safefast.service.procurement.ProcurementPlanService;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/purchase_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseOrderController {

    @Autowired
    private ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    private ProcurementPlanService procurementPlanService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderController(ProcurementPlanService procurementPlanService, PurchaseOrderService purchaseOrderService) {
        this.procurementPlanService = procurementPlanService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/purchase_order")
    public String createPurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
        model.addAttribute("purchaseOrder", purchaseOrder);
        return "redirect:/purchase_order/purchase_order";
    }

    @PostMapping("/create")
    public String createPurchaseOrder(@RequestBody List<PurchaseOrderRequest> purchaseOrderRequests) {
        for (PurchaseOrderRequest request : purchaseOrderRequests) {
            ProcurementPlan procurementPlan = procurementPlanRepository.findById(request.getProcPlanNumber()).orElseThrow(() -> new IllegalArgumentException("Invalid procurement plan number"));

            String purchOrderNumber = purchaseOrderService.generateNextPurchOrderNumber();

            PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                    .purchOrderNumber(purchOrderNumber)
                    .purchOrderQuantity(request.getPurchOrderQuantity())
                    .receiveDuedate(request.getReceiveDuedate())
                    .procPlanNumber(request.getProcPlanNumber())
                    .purchOrderDate(request.getReceiveDuedate())
                    .coOpCompany(procurementPlan.getCoOpCompany())
                    .item(procurementPlan.getItem())
                    .build();

            purchaseOrderRepository.save(purchaseOrder);
        }
        return "Purchase orders created successfully";
    }


    @GetMapping("/getPlan")
    @ResponseBody
    public ProcurementPlan getProcurementPlan(@RequestParam String procPlanNumber) {
        return purchaseOrderService.getProcurementPlanByNumber(procPlanNumber)
                .orElseThrow(() -> new RuntimeException("조달계획 정보를 불러오지 못했습니다."));
    }

    @GetMapping("/purchase_order")
    public String showPurchaseOrderPage(Model model) {
        List<ProcurementPlan> procurementPlans = procurementPlanService.getAllProcurementPlans();
        model.addAttribute("procurementPlans", procurementPlans);
        return "purchase_order/purchase_order";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/{purchOrderNumber}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable String purchOrderNumber) {
        Optional<PurchaseOrder> purchaseOrder = Optional.ofNullable(purchaseOrderRepository.findByPurchOrderNumber(purchOrderNumber));
        if (purchaseOrder.isPresent()) {
            return ResponseEntity.ok(purchaseOrder.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
