package org.zerock.safefast.controller.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProgressCheckItem;
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

    @GetMapping("/purchaseOrders")
    public List<PurchaseOrderResponse> getPurchaseOrders() {
        return purchaseOrderService.getPurchaseOrders();
    }


    @GetMapping("/progress-check")
    public String progressCheck(Model model) {
        return "/progress-check/progress_check_item";
    }

    // 모달1에서 검수계획 등록 완료 버튼을 눌렀을 때 요청 처리
    @PostMapping("/submit_inspection_plan")
    public String submitInspectionPlan(Model model) {

        return "redirect:/progress-check/progress_check_item";
    }


//    @Autowired
//    ProcurementPlanService procurementPlanService; //발주번호를 가져오는 서비스
//
//    @GetMapping("/progress")
//    public String progress(Model model) {
//        String poNumber = procurementPlanService.getPoNumber(); //데이터베이스에서 발주번호 가져오기
//        model.addAttribute("poNumber", poNumber); //모델에 발주번호 추가
//        return "/progress/progress_item";
//    }
}
