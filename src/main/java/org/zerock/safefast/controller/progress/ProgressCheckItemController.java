package org.zerock.safefast.controller.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.service.progress.ProgressCheckItemService;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/progress_check_item")
public class ProgressCheckItemController {

    private final PurchaseOrderService purchaseOrderService;
    private final ProgressCheckItemService progressCheckItemService;

    public ProgressCheckItemController(PurchaseOrderService purchaseOrderService, ProgressCheckItemService progressCheckItemService) {
        this.purchaseOrderService = purchaseOrderService;
        this.progressCheckItemService = progressCheckItemService;
    }

    @PostMapping("/progress_check/purchase_order_details")
    @ResponseBody
    public List<PurchaseOrderResponse> getPurchaseOrderDetails(@RequestBody List<String> checkedOrders) {
        return purchaseOrderService.getPurchaseOrderDetails(checkedOrders);
    }

    @GetMapping("/progress_check/purchase_order_details")
    @ResponseBody
    public PurchaseOrderResponse getPurchaseOrderDetails(@RequestParam String purchOrderNumber) {
        return purchaseOrderService.getPurchaseOrderDetails(purchOrderNumber);
    }

    @GetMapping("/progress_check_item")
    public String progressCheck(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "/progress_check_item/progress_check_item";
    }

    @GetMapping("/purchaseOrders")
    @ResponseBody
    public List<PurchaseOrderResponse> getPurchaseOrders() {
        return purchaseOrderService.getPurchaseOrders();
    }

    @GetMapping("/list/{purchOrderNumber}")
    @ResponseBody
    public List<ProgressCheckItem> getProgressCheckItems(@PathVariable String purchOrderNumber) {
        return progressCheckItemService.getProgressCheckItemsByPurchOrderNumber(purchOrderNumber);
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveProgressCheckItems(@RequestBody List<ProgressCheckItem> progressCheckItems) {
        progressCheckItemService.saveOrUpdateProgressCheckItems(progressCheckItems);
        return "검수계획이 성공적으로 등록되었습니다.";
    }
}
