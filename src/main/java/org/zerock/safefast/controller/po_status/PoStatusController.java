package org.zerock.safefast.controller.po_status;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/po_status")
public class PoStatusController {

    @GetMapping("/po_status")
    public String showPo_status() {
        return "po_status/po_status";
    }
}
