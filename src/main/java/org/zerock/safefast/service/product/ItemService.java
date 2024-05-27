package org.zerock.safefast.service.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.entity.Assy;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Part;
import org.zerock.safefast.entity.Unit;
import org.zerock.safefast.repository.AssyRepository;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.PartRepository;
import org.zerock.safefast.repository.UnitRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final UnitRepository unitRepository;
    private final AssyRepository assyRepository;
    private final PartRepository partRepository;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public List<Assy> getAllAssys() {
        return assyRepository.findAll();
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void registerItem(Item item, MultipartFile blueprintFile) {

        String itemCode = generateNextItemCode(item.getUnit().getUnitCode(), item.getAssy().getAssyCode(), item.getPart().getPartCode());
        item.setItemCode(itemCode);

        if (!blueprintFile.isEmpty()) {
            try {
                String uploadDir = "uploads";
                String fileName = UUID.randomUUID().toString() + "_" + blueprintFile.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(blueprintFile.getInputStream(), filePath);
                item.setBlueprintOriginName(blueprintFile.getOriginalFilename());
                item.setBlueprintSaveName(fileName);
            } catch (IOException e) {
                log.error("파일을 저장하는 동안 오류가 발생했습니다.", e);
            }
        }

        itemRepository.save(item);
    }

    private String generateNextItemCode(String unitCode, String assyCode, String partCode) {
        String maxItemCode = itemRepository.findMaxItemCode(unitCode, assyCode, partCode);
        String baseCode = unitCode + assyCode + partCode;
        if (maxItemCode != null && maxItemCode.startsWith(baseCode)) {
            int nextNumber = Integer.parseInt(maxItemCode.substring(baseCode.length() + 1)) + 1;
            return String.format("%s-%03d", baseCode, nextNumber);
        } else {
            return String.format("%s-001", baseCode);
        }
    }
}