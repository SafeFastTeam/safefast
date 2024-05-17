package org.zerock.safefast.controller.contract;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.CoOpCompanyRepository;
import org.zerock.safefast.repository.ContractRepository;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.service.contract.ContractService;
import org.zerock.safefast.service.product.ItemService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
@Log4j2
public class ContractController {

    private final ContractService contractService;
    private final CoOpCompanyRepository coOpCompanyRepository;
    private final ItemRepository itemRepository;

    @GetMapping("/register")
    public String showContract(Model model) {
        List<Item> items = contractService.getAllItems();
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("items", items);
        model.addAttribute("contracts", contracts);
        return "contract/register";
    }

    @GetMapping("/company")
    @ResponseBody
    public ResponseEntity<CoOpCompany> getCompanyByBusinessNumber(@RequestParam("businessNumber") String businessNumber) {
        CoOpCompany company = coOpCompanyRepository.findById(businessNumber)
                .orElse(null);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public String registerContract(@RequestParam("coOpCompany") String businessNumber,
                                   @RequestParam("itemCode") String itemCode,
                                   @RequestParam("itemPrice") int itemPrice,
                                   @RequestParam("leadTime") int leadTime,
                                   @RequestParam("contractOriginName") MultipartFile contractFile,
                                   @RequestParam("note") String note) {

        CoOpCompany coOpCompany = coOpCompanyRepository.findById(businessNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CoOpCompany ID: " + businessNumber));

        Item item = itemRepository.findById(itemCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Item Code: " + itemCode));


        Contract contract = Contract.builder()
                .coOpCompany(coOpCompany)
                .item(item)
                .itemPrice(itemPrice)
                .leadTime(leadTime)
                .note(note)
                .contractOriginName(contractFile.getOriginalFilename())
                .contractSaveName(contractFile.getOriginalFilename())
                .build();

        contractService.registerContract(contract, contractFile);

        return "redirect:/contract/register";
    }

}
