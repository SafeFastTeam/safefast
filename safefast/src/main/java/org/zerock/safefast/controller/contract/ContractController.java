package org.zerock.safefast.controller.contract;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.service.product.ItemService;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
@Log4j2
public class ContractController {

    //private final ContractService contractService;
    private final ItemService itemService;



}
