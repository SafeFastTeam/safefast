package org.zerock.safefast.controller.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.safefast.entity.InventoryItem;
import org.zerock.safefast.entity.Releases;
import org.zerock.safefast.service.inventory.InventoryService;
import org.zerock.safefast.service.releases.ReleasesService;

import java.text.NumberFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Log4j2
public class InventoryController {

    private final InventoryService inventoryService;
    private final ReleasesService releasesService;

    // 재고 페이지를 보여주는 메서드
    @GetMapping("/inventory")
    public String showInventoryPage(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<InventoryItem> inventoryItemPage = inventoryService.getAllInventoryItemsPaged(pageRequest);
        List<InventoryItem> inventoryItems = inventoryItemPage.getContent();
        int totalPages = inventoryItemPage.getTotalPages();
        int currentPage = inventoryItemPage.getNumber();

        // InventoryService를 통해 재고 데이터를 가져옵니다.
        List<Releases> releasesList = inventoryService.getAllReleases();

        Map<String, Map<String, Object>> inventoryData = new HashMap<>();

        for (Releases release : releasesList) {
            String itemCode = release.getItem().getItemCode();
            int receiveQuantity = release.getItem().getReceive().stream()
                    .mapToInt(receive -> receive.getReceiveQuantity())
                    .sum();
            int releaseQuantity = release.getReleaseQuantity();
            int price = release.getItem().getContracts().isEmpty() ? 0 : release.getItem().getContracts().get(0).getItemPrice();
            int initialQuantity = 0; // Assuming initial quantity is 0
            int finalQuantity = initialQuantity + receiveQuantity - releaseQuantity;
            int totalAmount = finalQuantity * price;

            // 숫자를 포맷팅합니다.
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
            String formattedTotalAmount = numberFormat.format(totalAmount);

            if (!inventoryData.containsKey(itemCode)) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("itemName", release.getItem().getItemName());
                itemData.put("dimensions", release.getItem().getWidth() + "x" + release.getItem().getLength() + "x" + release.getItem().getHeight());
                itemData.put("material", release.getItem().getMaterial());
                itemData.put("initialQuantity", initialQuantity);
                itemData.put("receiveQuantity", receiveQuantity);
                itemData.put("releaseQuantity", releaseQuantity);
                itemData.put("finalQuantity", finalQuantity);
                itemData.put("price", price);
                itemData.put("totalAmount", formattedTotalAmount); // 포맷된 값 저장
                inventoryData.put(itemCode, itemData);
            } else {
                Map<String, Object> itemData = inventoryData.get(itemCode);
                itemData.put("releaseQuantity", (int) itemData.get("releaseQuantity") + releaseQuantity);
                finalQuantity = initialQuantity + (int) itemData.get("receiveQuantity") - (int) itemData.get("releaseQuantity");
                totalAmount = finalQuantity * price;
                formattedTotalAmount = numberFormat.format(totalAmount);
                itemData.put("finalQuantity", finalQuantity);
                itemData.put("totalAmount", formattedTotalAmount); // 포맷된 값 저장
            }
        }
        model.addAttribute("inventoryItems", inventoryItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", size);
        model.addAttribute("inventoryData", inventoryData);

        return "inventory/inventory"; // inventory.html 템플릿을 반환합니다.
    }


    // 재고 검색을 처리하는 메서드
    @PostMapping("/inventory/search")
    public String searchInventory(@RequestParam("keyword") String keyword,
                                  @RequestParam("searchType") String searchType,
                                  Model model) {
        // 검색 타입에 따라 검색을 처리합니다.
        List<InventoryItem> searchResults;
        switch (searchType) {
            case "itemCode":
                searchResults = inventoryService.searchInventoryByItemCode(keyword);
                break;
            case "itemName":
                searchResults = inventoryService.searchInventoryByItemName(keyword);
                break;
            case "material":
                searchResults = inventoryService.searchInventoryByMaterial(keyword);
                break;
            default:
                searchResults = new ArrayList<>();
                break;
        }

        // 검색 결과를 모델에 추가하여 뷰에 전달합니다.
        model.addAttribute("inventoryItems", searchResults);

        return "inventory/inventory"; // inventory.html 템플릿을 반환합니다.
    }

}
