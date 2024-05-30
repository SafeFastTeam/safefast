package org.zerock.safefast.controller.contract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.service.contract.ContractService;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.CoOpCompanyRepository;
import org.zerock.safefast.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
@Log4j2
public class ContractController {

    @Value("${file.upload-dir}")
    private String uploadDir;

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
        CoOpCompany company = coOpCompanyRepository.findById(businessNumber).orElse(null);
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            log.error("파일을 저장하는 동안 오류가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 중 오류 발생");
        }
    }

    @GetMapping("/file/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Resource resource = contractService.loadFileAsResource(fileName);
        String encodedFileName = encodeFileName(resource.getFilename());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                .body(resource);
    }
    private String encodeFileName(String fileName) {
        try {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode file name", e);
        }
    }
}
