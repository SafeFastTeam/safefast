package org.zerock.safefast.controller.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.safefast.entity.InventoryItem;
import org.zerock.safefast.service.inventory.InventoryService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // 재고 페이지를 보여주는 메서드
    @GetMapping("/inventory")
    public String showInventoryPage(Model model) {
        // InventoryService를 통해 재고 데이터를 가져옵니다.
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();

        // 모델에 재고 데이터를 추가하여 뷰에 전달합니다.
        model.addAttribute("inventoryItems", inventoryItems);

        return "inventory"; // inventory.html 템플릿을 반환합니다.
    }

}
