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
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

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

    public void registerItem(Item item, MultipartFile file) {
        // 아이템 정보 저장
        itemRepository.save(item);

        // 파일 저장
        if (!file.isEmpty()) {
            try {
                String uploadDir = "uploads"; // 파일 업로드 디렉토리
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // 파일 이름 생성
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                item.setBlueprintOriginName(file.getOriginalFilename()); // 파일 원본 이름 저장
                item.setBlueprintSaveName(fileName); // 파일 저장된 이름 저장
            } catch (IOException e) {
                // 파일 저장 과정에서 예외 발생 시 로깅
                logger.error("파일을 저장하는 동안 오류가 발생했습니다.", e);

            }
        }
    }

    public Item findItemByCode(String itemCode) {
        Optional<Item> optionalItem = Optional.ofNullable(itemRepository.findByItemCode(itemCode));
        return optionalItem.orElse(null); // Optional을 사용하여 Item이 없을 경우 null을 반환하도록 처리
    }


}
