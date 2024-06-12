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
import org.zerock.safefast.entity.Releases;
import org.zerock.safefast.service.inventory.InventoryService;
import org.zerock.safefast.service.releases.ReleasesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            if (!inventoryData.containsKey(itemCode)) {
                inventoryData.put(itemCode, new HashMap<>());
                inventoryData.get(itemCode).put("itemName", release.getItem().getItemName());
                inventoryData.get(itemCode).put("dimensions", release.getItem().getWidth() + "x" + release.getItem().getLength() + "x" + release.getItem().getHeight());
                inventoryData.get(itemCode).put("material", release.getItem().getMaterial());
                inventoryData.get(itemCode).put("initialQuantity", initialQuantity);
                inventoryData.get(itemCode).put("receiveQuantity", receiveQuantity);
                inventoryData.get(itemCode).put("releaseQuantity", releaseQuantity);
                inventoryData.get(itemCode).put("finalQuantity", finalQuantity);
                inventoryData.get(itemCode).put("price", price);
                inventoryData.get(itemCode).put("totalAmount", totalAmount);
            } else {
                inventoryData.get(itemCode).put("releaseQuantity", (int)inventoryData.get(itemCode).get("releaseQuantity") + releaseQuantity);
                finalQuantity = initialQuantity + (int)inventoryData.get(itemCode).get("receiveQuantity") - (int)inventoryData.get(itemCode).get("releaseQuantity");
                totalAmount = finalQuantity * price;
                inventoryData.get(itemCode).put("finalQuantity", finalQuantity);
                inventoryData.get(itemCode).put("totalAmount", totalAmount);
            }
        }
        model.addAttribute("inventoryItems", inventoryItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", size);
        model.addAttribute("inventoryData", inventoryData);

        return "/inventory/inventory"; // inventory.html 템플릿을 반환합니다.
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

        return "/inventory/inventory"; // inventory.html 템플릿을 반환합니다.
    }

}
