package org.zerock.safefast.service.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.InventoryItem;
import org.zerock.safefast.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    // 모든 재고 아이템을 가져오는 메서드
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

}
