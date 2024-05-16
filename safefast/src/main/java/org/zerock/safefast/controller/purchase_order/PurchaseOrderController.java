package org.zerock.safefast.controller.purchase_order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/purchase_order")
public class PurchaseOrderController {

    @GetMapping("/purchase_order")
    public String showPurchaseOrder() {
        return "purchase_order/purchase_order";
    }

}
