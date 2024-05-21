package org.zerock.safefast.controller.inventory_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory_management")
public class InventoryManagementController {

    @GetMapping("/inventory_management")
    public String showPo_status() {
        return "/inventory_management/inventory_management";
    }
}
