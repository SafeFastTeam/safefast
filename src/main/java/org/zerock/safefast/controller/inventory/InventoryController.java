package org.zerock.safefast.controller.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.safefast.entity.InventoryItem;
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
    public String showInventoryPage(Model model) {
        // InventoryService를 통해 재고 데이터를 가져옵니다.
        List<Releases> releasesList = inventoryService.getAllReleases();

        Map<String, Integer> itemCodeQuantities  = new HashMap<>();
        for (Releases release : releasesList) {
            String itemCode = release.getItem().getItemCode();
            Integer quantity = release.getReleaseQuantity();
            itemCodeQuantities.put(itemCode, itemCodeQuantities.getOrDefault(itemCode, 0) + quantity);
        }

        // 모델에 재고 데이터를 추가하여 뷰에 전달합니다.
        model.addAttribute("releases", releasesList);
        model.addAttribute("itemCodeQuantities", itemCodeQuantities);

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
