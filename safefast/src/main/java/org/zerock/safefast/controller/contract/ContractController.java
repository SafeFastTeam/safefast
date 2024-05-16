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
import org.zerock.safefast.repository.CoOpCompanyRepository;
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
    private final CoOpCompanyRepository coOpCompanyRepository;

    @GetMapping("/register")
    public String showContract(Model model) {
        List<Item> items = contractService.getAllItems();
        List<Contract> contracts = contractService.getAllContractsWithDetails();
        model.addAttribute("items", items);
        model.addAttribute("contracts", contracts);
        return "contract/register";
    }

    // 추가: 사업자 번호에 해당하는 업체 정보를 반환하는 컨트롤러 메서드
    @GetMapping("/company")
    @ResponseBody
    public ResponseEntity<CoOpCompany> getCompanyByBusinessNumber(@RequestParam String businessNumber) {
        CoOpCompany company = coOpCompanyRepository.findById(businessNumber)
                .orElse(null);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}