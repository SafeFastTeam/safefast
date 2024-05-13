package org.zerock.safefast.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.service.product.ItemService;


import java.io.IOException;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("item", new Item());
        return "item/register";
    }

    @PostMapping("/register")
    public String registerItem(@ModelAttribute("item") Item item,
                               @RequestParam("blueprintFile") MultipartFile blueprintFile) throws IOException {
        // 여기서 아이템 및 첨부 파일을 저장하는 서비스 메서드를 호출합니다.
        itemService.registerItem(item, blueprintFile);
        return "redirect:/items/register?success";
    }
}
