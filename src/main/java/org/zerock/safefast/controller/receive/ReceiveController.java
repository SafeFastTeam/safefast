package org.zerock.safefast.controller.receive;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.dto.receive.UpdateReceiveQuantityDTO;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.Receive;
import org.zerock.safefast.service.receive.ReceiveService;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/register")
    public ResponseEntity<Void> registerReceive(@RequestBody List<UpdateReceiveQuantityDTO> receiveData) {
        try {
            log.info("Received request to register receive data: {}", receiveData);

            // 입고 수량을 업데이트하는 메서드 호출
            receiveService.updateStockFromReceive(receiveData);

            log.info("Receive data registered successfully.");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Failed to register receive data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
