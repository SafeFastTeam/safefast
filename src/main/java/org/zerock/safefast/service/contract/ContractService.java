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
import org.zerock.safefast.dto.item.ItemDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractService.class);
    private final ContractRepository contractRepository;
    private final ItemRepository itemRepository;

    public PageResultDTO<ItemDTO, Item> getLists(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("itemCode").descending());
        Page<Item> result = itemRepository.findAll(pageable);
        Function<Item, ItemDTO> fn = this::entityToDto;
        return new PageResultDTO<>(result, fn);
    }

    public ItemDTO entityToDto(Item entity) {
        ItemDTO dto = ItemDTO.builder()
            .itemCode(entity.getItemCode())
            .itemName(entity.getItemName())
            .width(entity.getWidth())
            .length(entity.getLength())
            .height(entity.getHeight())
            .material(entity.getMaterial())
            .blueprintOriginName(entity.getBlueprintOriginName())
            .blueprintSaveName(entity.getBlueprintSaveName())
            .contracts(entity.getContracts().stream()
                    .map(this::contractToDto)
                    .collect(Collectors.toList()))
            .build();

        return dto;
    }

    public ContractDTO contractToDto(Contract entity) {
        ContractDTO contractDTO = ContractDTO.builder()
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
        return contractDTO;
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

    private String generateNextContractNumber() {
        String maxContractNumber = contractRepository.findMaxContractNumber();
        if (maxContractNumber != null && maxContractNumber.startsWith("CON-")) {
            int nextNumber = Integer.parseInt(maxContractNumber.split("-")[1]) + 1;
            return String.format("CON-%03d", nextNumber);
        } else {
            return "CON-001";
        }
    }
}
