package org.zerock.safefast.service.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.InventoryItem;
import org.zerock.safefast.entity.Item; // Item 엔티티 import
import org.zerock.safefast.entity.Receive; // Receive 엔티티 import
import org.zerock.safefast.entity.Releases; // Releases 엔티티 import
import org.zerock.safefast.entity.Contract; // Contract 엔티티 import
import org.zerock.safefast.repository.InventoryRepository;
import org.zerock.safefast.repository.ItemRepository; // ItemRepository import
import org.zerock.safefast.repository.ReceiveRepository; // ReceiveRepository import
import org.zerock.safefast.repository.ReleasesRepository; // ReleasesRepository import
import org.zerock.safefast.repository.ContractRepository; // ContractRepository import

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;
    private final ReceiveRepository receiveRepository;
    private final ReleasesRepository releasesRepository;
    private final ContractRepository contractRepository;

    // 모든 재고 아이템을 가져오는 메서드
    public List<InventoryItem> getAllInventoryItems() {
        // 각 테이블로부터 데이터를 가져옵니다.
        List<Item> items = itemRepository.findAll();
        List<Receive> receives = receiveRepository.findAll();
        List<Releases> releases = releasesRepository.findAll();
        List<Contract> contracts = contractRepository.findAll();

        // 재고 아이템을 저장할 리스트를 생성합니다.
        List<InventoryItem> inventoryItems = new ArrayList<>();

        // 각 아이템에 대한 재고 정보를 계산하여 리스트에 추가합니다.
        for (Item item : items) {
            // 해당 품목에 대한 기초 재고, 입고 수량, 출고 수량을 계산합니다.
            int initialInventory = calculateInitialInventory(item, receives, releases, contracts);
            int incomingQuantity = calculateIncomingQuantity(item, receives);
            int outgoingQuantity = calculateOutgoingQuantity(item, releases);
            // 기초 재고와 입고, 출고 수량을 이용하여 기말 재고를 계산합니다.
            int endingInventory = initialInventory + incomingQuantity - outgoingQuantity;
            // 단가(원)은 재고 정보에 따라 설정합니다.
            int unitPrice = calculateUnitPrice(item);
            // 재고 금액은 기말 재고와 단가를 곱하여 계산합니다.
            int inventoryAmount = endingInventory * unitPrice;

            // 재고 아이템 객체를 생성하고 리스트에 추가합니다.
            InventoryItem inventoryItem = InventoryItem.builder()
                    .item(item)
                    .quantityAvailable(endingInventory)
                    .build();
            inventoryItems.add(inventoryItem);
        }

        return inventoryItems;
    }

    // 해당 품목에 대한 기초 재고를 계산하는 메서드
    private int calculateInitialInventory(Item item, List<Receive> receives, List<Releases> releases, List<Contract> contracts) {
        // 기초 재고를 계산하여 반환하는 로직을 구현합니다.
        // 여기서는 간단히 0으로 설정합니다.
        return 0;
    }

    // 해당 품목에 대한 입고 수량을 계산하는 메서드
    private int calculateIncomingQuantity(Item item, List<Receive> receives) {
        // 입고 수량을 계산하여 반환하는 로직을 구현합니다.
        // 여기서는 간단히 60000으로 설정합니다.
        return 60000;
    }

    // 해당 품목에 대한 출고 수량을 계산하는 메서드
    private int calculateOutgoingQuantity(Item item, List<Releases> releases) {
        // 출고 수량을 계산하여 반환하는 로직을 구현합니다.
        // 여기서는 간단히 500으로 설정합니다.
        return 500;
    }

    // 해당 품목에 대한 단가(원)를 계산하는 메서드
    private int calculateUnitPrice(Item item) {
        // 단가를 계산하여 반환하는 로직을 구현합니다.
        // 여기서는 간단히 1000으로 설정합니다.
        return 1000;
    }

    // 재고 검색을 처리하는 메서드
    public List<InventoryItem> searchInventoryByItemCode(String keyword) {
        // InventoryRepository를 통해 품목코드로 재고를 검색합니다.
        List<InventoryItem> searchResults = inventoryRepository.findByItem_ItemCodeContaining(keyword);
        return searchResults;
    }

    public List<InventoryItem> searchInventoryByItemName(String keyword) {
        // InventoryRepository를 통해 품목명으로 재고를 검색합니다.
        List<InventoryItem> searchResults = inventoryRepository.findByItem_ItemNameContaining(keyword);
        return searchResults;
    }

    public List<InventoryItem> searchInventoryByMaterial(String keyword) {
        // InventoryRepository를 통해 재질로 재고를 검색합니다.
        List<InventoryItem> searchResults = inventoryRepository.findByItem_MaterialContaining(keyword);
        return searchResults;
    }

}