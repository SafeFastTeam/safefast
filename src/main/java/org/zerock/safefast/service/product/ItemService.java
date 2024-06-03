package org.zerock.safefast.service.product;

/*import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.tyoes.dsl.BooleanExpression;*/

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.Assy;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Part;
import org.zerock.safefast.entity.Unit;
import org.zerock.safefast.repository.AssyRepository;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.PartRepository;
import org.zerock.safefast.repository.UnitRepository;
import org.zerock.safefast.service.SafefastService;

import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@Primary
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final Path uploadDir = Paths.get("upload");

    private final UnitRepository unitRepository;
    private final AssyRepository assyRepository;
    private final PartRepository partRepository;
    private final QuantityRepository quantityRepository;

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

    public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("itemCode").descending());
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
                .build();

        return dto;
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

        Optional<Item> existingItemOpt = itemRepository.findByItemCode(item.getItemCode());
        if (existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();
            // 기존 Item이 존재하면 병합하여 업데이트
            existingItem.setUnit(item.getUnit());
            existingItem.setAssy(item.getAssy());
            existingItem.setPart(item.getPart());
            existingItem.setItemName(item.getItemName());
            existingItem.setWidth(item.getWidth());
            existingItem.setLength(item.getLength());
            existingItem.setHeight(item.getHeight());
            existingItem.setMaterial(item.getMaterial());
            existingItem.setBlueprintOriginName(item.getBlueprintOriginName());
            existingItem.setBlueprintSaveName(item.getBlueprintSaveName());
            itemRepository.save(existingItem);

            // 기존 상품의 수량 업데이트
            Quantity existingQuantity = quantityRepository.findByItem(existingItem);
            if (existingQuantity != null) {
                existingQuantity.setAllQuantity(0); // 기본적으로 수량을 0으로 설정
                quantityRepository.save(existingQuantity);
            }
        } else {
            // 새로운 Item을 저장
            itemRepository.save(item);

            // 새로운 상품의 수량 생성
            Quantity quantity = new Quantity();
            quantity.setItem(item);
            quantity.setAllQuantity(0); // 새로운 상품이므로 수량은 0으로 설정
            quantityRepository.save(quantity);
        }
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

    public PageResultDTO<ItemDTO, Item> searchItems(PageRequestDTO pageRequestDTO, String keyword) {
        // PageRequestDTO를 이용하여 Pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("itemCode").descending());

        // itemRepository를 사용하여 keyword를 포함하는 아이템을 검색하고 페이징 처리하여 결과를 가져옴
        Page<Item> result = itemRepository.findByItemNameContaining(keyword, pageable);

        // 검색 결과를 ItemDTO로 변환하는 Function 객체 생성
        Function<Item, ItemDTO> fn = this::entityToDto;

        // PageResultDTO 객체 생성하여 반환
        return new PageResultDTO<>(result, fn);
    }

    public Resource loadFileAsResource(String fileName) throws IOException {
        try {
            Path filePath = uploadDir.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new IOException("File not found: " + fileName, ex);
        }
    }


}