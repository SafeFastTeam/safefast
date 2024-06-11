package org.zerock.safefast.controller.invoicing;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String showInvoicingPage(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        // 페이지와 사이즈를 기반으로 페이지 요청 객체 생성
        PageRequest pageRequest = PageRequest.of(page, size);
        // 페이지에 해당하는 PurchaseOrder 데이터 가져오기
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderService.getPurchaseOrders(pageRequest);
        // 현재 페이지의 PurchaseOrder 목록
        List<PurchaseOrder> purchaseOrders = purchaseOrderPage.getContent();
        // 총 페이지 수
        int totalPages = purchaseOrderPage.getTotalPages();
        // 현재 페이지 번호
        int currentPage = purchaseOrderPage.getNumber();
        // PurchaseOrder 데이터를 모델에 추가
        model.addAttribute("purchaseOrders", purchaseOrders);
        // 페이징 관련 데이터를 모델에 추가
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", size);
        // 뷰를 반환
        return "invoicing/invoicing";
    }
}