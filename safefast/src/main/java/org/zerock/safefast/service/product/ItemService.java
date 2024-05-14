package org.zerock.safefast.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.ItemRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void registerItem(Item item, MultipartFile blueprintFile) throws IOException {
        // 아이템 저장
        itemRepository.save(item);

        // 첨부 파일 저장
        if (blueprintFile != null && !blueprintFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(blueprintFile.getOriginalFilename());
            String uploadDir = "./blueprints"; // 파일을 저장할 디렉토리 경로

            // 디렉토리 생성
            Files.createDirectories(Paths.get(uploadDir));

            // 파일 경로 설정
            Path filePath = Paths.get(uploadDir, fileName);

            // 파일 저장
            Files.copy(blueprintFile.getInputStream(), filePath);
        }
    }
}
