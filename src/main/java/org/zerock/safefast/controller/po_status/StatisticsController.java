package org.zerock.safefast.controller.po_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.safefast.service.purchase_order.PurchaseOrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatisticsController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/counts")
    public Map<String, Long> getCounts(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, Long> counts = new HashMap<>();
        counts.put("totalPlans", purchaseOrderService.countTotalPlans(startDate, endDate));
        counts.put("issuedOrders", purchaseOrderService.countIssuedOrders(startDate, endDate));
        counts.put("progressChecks", purchaseOrderService.countProgressChecks(startDate, endDate));
        counts.put("completedProcurements", purchaseOrderService.countCompletedProcurements(startDate, endDate));

        return counts;
    }
}
