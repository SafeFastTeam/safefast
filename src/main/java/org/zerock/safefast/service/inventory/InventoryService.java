package org.zerock.safefast.service.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.*;
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
            int initialInventory = 0; // 기초 재고 하드 코딩
            int incomingQuantity = 600; // 입고 수량 하드 코딩
            int outgoingQuantity = 500; // 출고 수량 하드 코딩
            // 기초 재고와 입고, 출고 수량을 이용하여 기말 재고를 계산합니다.
            int endingInventory = initialInventory + incomingQuantity - outgoingQuantity;
            // 단가(원)은 재고 정보에 따라 설정합니다.
            int unitPrice = 300; // 단가(원) 하드 코딩
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

    // 해당 품목에 대한 입고 수량을 데이터베이스에서 조회하여 반환하는 메서드
//    private int calculateIncomingQuantity(Item item) {
    // 데이터베이스에서 해당 품목에 대한 입고 수량을 조회하여 반환하는 로직을 작성
/*        Integer incomingQuantity = receiveRepository.sumQuantityByItem(item);
        return incomingQuantity != null ? incomingQuantity : 0;*/
//    }

    // 해당 품목에 대한 출고 수량을 계산하여 반환하는 메서드
//    private int calculateOutgoingQuantity(Item item) {
    // 데이터베이스에서 해당 품목에 대한 출고 수량을 조회하여 반환하는 로직을 작성
/*        String itemCode = item.getItemCode(); // 아이템의 itemCode 가져오기
        Integer outgoingQuantity = releasesRepository.sumQuantityByItem(itemCode);
        return outgoingQuantity != null ? outgoingQuantity : 0;*/
//    }

    // 해당 품목에 대한 단가(원)를 계산하는 메서드
//    private int calculateUnitPrice(Item item) {
    // ContractRepository를 사용하여 해당 품목에 대한 계약 정보를 조회합니다.
/*        Contract contract = contractRepository.findByItem(item)
                .orElseThrow(() -> new RuntimeException("해당 품목에 대한 계약 정보를 찾을 수 없습니다."));

        // 조회된 계약 정보에서 단가 정보를 가져옵니다.
        int unitPrice = contract.getItemPrice();

        return unitPrice;*/
//    }

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

    public List<Releases> getAllReleases() {
        return releasesRepository.findAll();
    }


    public Page<InventoryItem> getAllInventoryItemsPaged(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }
}