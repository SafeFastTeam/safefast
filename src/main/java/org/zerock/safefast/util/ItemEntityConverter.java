package org.zerock.safefast.util;

import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.entity.Item;

public class ItemEntityConverter {

    public static ItemDTO entityToDto(Item item) {
        // Item 엔티티를 ItemDTO로 변환하는 코드
        ItemDTO dto = new ItemDTO();
        dto.setItemCode(item.getItemCode());
        dto.setItemName(item.getItemName());
        // 나머지 필드에 대한 변환 작업

        return dto;
    }
}
