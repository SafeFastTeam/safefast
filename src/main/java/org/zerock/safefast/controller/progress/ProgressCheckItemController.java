package org.zerock.safefast.controller.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.service.progress.ProgressCheckItemService;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String progressCheck(PageRequestDTO pageRequestDTO, Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        PageResultDTO<PurchaseOrder, PurchaseOrder> result = progressCheckItemService.getList(pageRequestDTO);

        // 각 PurchaseOrder에 대해 maxProgCheckOrder와 maxProgCheckResult 계산
        Map<String, Integer> maxProgCheckOrders = purchaseOrders.stream()
                .collect(Collectors.toMap(
                        PurchaseOrder::getPurchOrderNumber,
                        po -> po.getProgressCheckItems().stream()
                                .mapToInt(ProgressCheckItem::getProgCheckOrder)
                                .max()
                                .orElse(0) // 비어 있는 경우 0으로 설정
                ));

        Map<String, Integer> sumProgCheckResults = purchaseOrders.stream()
                .collect(Collectors.toMap(
                        PurchaseOrder::getPurchOrderNumber,
                        po -> po.getProgressCheckItems().stream()
                                .mapToInt(item -> Integer.parseInt(item.getProgCheckResult())) // 문자열을 정수로 변환
                                .sum() // 합계 계산
                ));

        Map<String, String> maxProgCheckResults = purchaseOrders.stream()
                .collect(Collectors.toMap(
                        PurchaseOrder::getPurchOrderNumber,
                        po -> po.getProgressCheckItems().stream()
                                .map(ProgressCheckItem::getProgCheckResult)
                                .max(String::compareTo)
                                .orElse("") // 비어 있는 경우 빈 문자열로 설정
                ));


        model.addAttribute("purchaseOrders", purchaseOrders);
        model.addAttribute("maxProgCheckOrders", maxProgCheckOrders);
        model.addAttribute("sumProgCheckResults", sumProgCheckResults);
        model.addAttribute("maxProgCheckResults", maxProgCheckResults);
        model.addAttribute("result", result);
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
    public String saveProgressCheckItems(@RequestBody List<ProgressCheckItem> progressCheckItems, @RequestParam("type") String type) {
        progressCheckItemService.saveOrUpdateProgressCheckItems(progressCheckItems);
        if (type.equals("plan")) {
            return "검수계획이 성공적으로 등록되었습니다.";
        } else if (type.equals("process")) {
            return "검수처리가 완료되었습니다.";
        } else {
            return "잘못된 요청입니다.";
        }
    }
}
