package org.zerock.safefast;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.util.ItemEntityConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SafefastApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testEntityToDto() {
        // 테스트 데이터 생성
        Item item = new Item();
        item.setItemCode("IT001");
        item.setItemName("Sample Item");
        // 나머지 필드에 대해서도 데이터 설정

        // 엔티티를 DTO로 변환
        ItemDTO dto = ItemEntityConverter.entityToDto(item);

        // 변환된 DTO의 각 필드를 확인하여 예상 값과 일치하는지 확인
        assertEquals("IT001", dto.getItemCode());
        assertEquals("Sample Item", dto.getItemName());
        // 나머지 필드에 대해서도 예상 값과 일치하는지 확인
    }

}
