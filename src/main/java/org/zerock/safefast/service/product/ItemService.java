package org.zerock.safefast.service.product;

/*import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.tyoes.dsl.BooleanExpression;*/
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.dto.ItemDTO;
import org.zerock.safefast.dto.PageRequestDTO;
import org.zerock.safefast.dto.PageResultDTO;
import org.zerock.safefast.entity.Assy;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Part;
import org.zerock.safefast.entity.Unit;
import org.zerock.safefast.repository.AssyRepository;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.PartRepository;
import org.zerock.safefast.repository.UnitRepository;
import org.zerock.safefast.service.SafefastService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.function.Function;

@Service
@Primary
@RequiredArgsConstructor
public class ItemService implements SafefastService {

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

    @Override
    public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("itemCode").descending());
        Page<Item> result = itemRepository.findAll(pageable);
        Function<Item, ItemDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

/*    public PageResultDTO<ItemDTO, Item> searchItems(String keyword, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable();
        Page<Item> result = itemRepository.findByItemNameContaining(keyword, pageable);
        return new PageResultDTO<>(result, ItemDTO::new);
    }*/

    @Override
    public String register(ItemDTO dto) {
        log.info("DTO------------------");

        Item entity = dtoToEntity(dto);

        itemRepository.save(entity);

        return entity.getItemCode();
    }

    @Override
    public Item dtoToEntity(ItemDTO dto) {
        Item entity = Item.builder()
                .itemCode(dto.getItemCode())
                .itemName(dto.getItemName())
                .width(dto.getWidth())
                .length(dto.getLength())
                .height(dto.getHeight())
                .material(dto.getMaterial())
                .blueprintOriginName(dto.getBlueprintOriginName())
                .build();
        return entity;
    }

    @Override
    public ItemDTO entityToDto(Item entity) {
        ItemDTO dto  = ItemDTO.builder()
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

    @Override
    public PageResultDTO<ItemDTO, Item> searchItems(PageRequestDTO pageRequestDTO, String keyword) {
        return null;
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