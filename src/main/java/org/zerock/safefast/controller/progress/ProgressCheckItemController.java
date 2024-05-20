package org.zerock.safefast.controller.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.service.progress.ProgressCheckItemService;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;

import java.util.List;

@Controller
/*@RequestMapping("/progress-check")*/
public class ProgressCheckItemController {

    private final PurchaseOrderService purchaseOrderService;

    public ProgressCheckItemController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/progress_check/purchase_order_details")
    public List<PurchaseOrderResponse> getPurchaseOrderDetails(@RequestBody List<String> checkedOrders) {
        // 체크된 발주에 대한 상세 정보를 가져오는 서비스 메서드 호출
        return purchaseOrderService.getPurchaseOrderDetails(checkedOrders);
    }

    @GetMapping("/purchaseOrders")
    public List<PurchaseOrderResponse> getPurchaseOrders() {
        return purchaseOrderService.getPurchaseOrders();
    }


    @GetMapping("/progress-check")
    public String progressCheck(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "/progress-check/progress_check_item";
    }






}
