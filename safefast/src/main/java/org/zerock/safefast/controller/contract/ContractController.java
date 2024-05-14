package org.zerock.safefast.controller.contract;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.ContractRepository;
import org.zerock.safefast.service.contract.ContractService;
import org.zerock.safefast.service.product.ItemService;

import java.util.List;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
@Log4j2
public class ContractController {

    private final ContractService contractService;
    private final ContractRepository contractRepository;

    @GetMapping("/register")
    public String showContract(Model model) {
        List<Item> items = contractService.getAllItems();
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("items", items);
        model.addAttribute("contracts", contracts);
        return "contract/register";
    }

//    @GetMapping("/company")
//    @ResponseBody
//    public ResponseEntity<CoOpCompany> getCompanyByBusinessNumber(@RequestParam String businessNumber) {
//        CoOpCompany company = contractRepository.findByCoOpCompany(businessNumber);
//        if (company != null) {
//            return ResponseEntity.ok(company);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}