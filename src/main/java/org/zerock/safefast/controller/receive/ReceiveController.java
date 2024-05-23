package org.zerock.safefast.controller.receive;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.service.receive.ReceiveService;

import java.util.List;

@Controller
@RequestMapping("/receive")
@RequiredArgsConstructor
@Log4j2
public class ReceiveController {

    private final ReceiveService receiveService;

    @GetMapping("/receive")
    public String showReceivePage(Model model) {
        List<ProcurementPlan> procurementPlanList = receiveService.getAllProcurementPlan();
        model.addAttribute("procurements", procurementPlanList);
        return "receive/receive";
    }

    @PostMapping("/receive")
    public ResponseEntity<String> addReceive(@RequestBody ReceiveDTO receiveDTO) {
        try {
            receiveService.addReceive(receiveDTO);
            return ResponseEntity.ok("{\"message\": \"등록되었습니다.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"등록에 실패했습니다.\"}");
        }
    }

}
