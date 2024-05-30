package org.zerock.safefast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.ItemRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class SafefastServiceImpl implements SafefastService{

    private final ItemRepository repository;

    @Override
    public String register(ItemDTO dto) {

        log.info("DTO------------------");
        log.info(dto);

        Item entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getItemCode();
    }

    @Override
    public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("itemCode").descending());

        Page<Item> result = repository.findAll(pageable);

        Function<Item, ItemDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn );
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
}
