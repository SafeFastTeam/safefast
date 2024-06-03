package org.zerock.safefast.service.contract;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.dto.contract.ContractDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.ContractRepository;
import org.zerock.safefast.repository.ItemRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractService.class);
    private final ContractRepository contractRepository;
    private final ItemRepository itemRepository;

    public PageResultDTO<ContractDTO, Contract> getLists(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("contractNumber").descending());
        Page<Contract> result = contractRepository.findAll(pageable);

        // Contract를 ContractDTO로 변환하는 함수 정의
        Function<Contract, ContractDTO> fn = this::entityDto;

        // 변환 함수를 이용하여 PageResultDTO 생성 후 반환
        return new PageResultDTO<>(result, fn);
    }

    public ContractDTO entityDto(Contract entity) {
        ContractDTO dto = ContractDTO.builder()
                .contractNumber(entity.getContractNumber())
                .itemPrice(entity.getItemPrice())
                .leadTime(entity.getLeadTime())
                .note(entity.getNote())
                .itemName(entity.getItem().getItemName())
                .businessNumber(entity.getCoOpCompany().getBusinessNumber())
                .itemCode(entity.getItem().getItemCode())
                .companyName(entity.getCoOpCompany().getCompanyName())
                .contractOriginName(entity.getContractOriginName())
                .contractSaveName(entity.getContractSaveName())
                .build();
        return dto;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public void registerContract(Contract contract, MultipartFile file) {
        contract.setContractNumber(generateNextContractNumber());
        contract.setContractDate(LocalDate.now());
        contractRepository.save(contract);

        if (!file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                contract.setContractOriginName(file.getOriginalFilename());
                contract.setContractSaveName(fileName);
                contractRepository.save(contract);
            } catch (IOException e) {
                log.error("파일을 저장하는 동안 오류가 발생했습니다.", e);
                throw new RuntimeException("Failed to store file", e);
            }
        }
    }

    public byte[] getContractFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir, filename);
        return Files.readAllBytes(filePath);
    }

    private String generateNextContractNumber() {
        String maxContractNumber = contractRepository.findMaxContractNumber();
        if (maxContractNumber != null && maxContractNumber.startsWith("CON-")) {
            int nextNumber = Integer.parseInt(maxContractNumber.split("-")[1]) + 1;
            return String.format("CON-%03d", nextNumber);
        } else {
            return "CON-001";
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
