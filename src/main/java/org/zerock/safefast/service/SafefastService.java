package org.zerock.safefast.service;

import org.springframework.stereotype.Repository;
import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.Item;

@Repository
public interface SafefastService {
    String register(ItemDTO dto);

    PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO);

    default Item dtoToEntity(ItemDTO dto) {
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

    default ItemDTO entityToDto(Item entity) {

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

    PageResultDTO<ItemDTO, Item> searchItems(PageRequestDTO pageRequestDTO, String keyword);
}
