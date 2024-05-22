package org.zerock.safefast.controller.invoicing;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;

import java.util.List;

@Controller
@RequestMapping("/invoicing")
public class InvoicingController {

    private final PurchaseOrderService purchaseOrderService;

    public InvoicingController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/invoicing")
    public String showInvoicingPage(Model model) {
        // PurchaseOrder 데이터를 가져옴
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        // PurchaseOrder 데이터를 모델에 추가
        model.addAttribute("purchaseOrders", purchaseOrders);
        // 뷰를 반환
        return "invoicing/invoicing";
    }
}
