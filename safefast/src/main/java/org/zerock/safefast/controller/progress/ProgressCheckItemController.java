package org.zerock.safefast.controller.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.service.procurement.ProcurementPlanService;
import org.zerock.safefast.service.progress.ProgressCheckItemService;

@Controller
public class ProgressCheckItemController {

    private final ProgressCheckItemService progressCheckItemService;

    @Autowired
    public ProgressCheckItemController(ProgressCheckItemService progressCheckItemService) { this.progressCheckItemService = progressCheckItemService; }

    @GetMapping("/progress-check")
    public String progressCheck() {
        return "/progress-check/progress_check_item";
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
