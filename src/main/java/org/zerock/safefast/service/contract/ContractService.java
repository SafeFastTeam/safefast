package org.zerock.safefast.service.contract;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.ContractRepository;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.repository.ItemRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractService.class);
    private final ContractRepository contractRepository;
    private final ItemRepository itemRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void registerContract(Contract contract, MultipartFile file) {

        String shortUuid = UUID.randomUUID().toString().substring(0, 8);
        contract.setContractNumber(shortUuid);
        contract.setContractDate(LocalDateTime.now());

        contractRepository.save(contract);

        if (!file.isEmpty()) {
            try {
                String uploadDir = "uploads";
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
            }
        }
    }


}