package org.zerock.safefast.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.safefast.entity.Assy;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Part;
import org.zerock.safefast.entity.Unit;
import org.zerock.safefast.service.product.ItemService;


import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/register")
    public String showItemForm(Model model) {
        List<Unit> units = itemService.getAllUnits();
        List<Assy> assies = itemService.getAllAssys();
        List<Part> parts = itemService.getAllParts();
        List<Item> items = itemService.getAllItems();
        model.addAttribute("units", units); // 모델에 Unit 정보 추가
        model.addAttribute("items", items);
        model.addAttribute("assies", assies);
        model.addAttribute("parts", parts);
        model.addAttribute("item", new Item());
        return "item/register";
    }

    @PostMapping("/register")
    public String registerItem(@RequestParam("itemCode") String itemCode,
                               @RequestParam("itemName") String itemName,
                               @RequestParam("width") Integer width,
                               @RequestParam("length") Integer length,
                               @RequestParam("height") Integer height,
                               @RequestParam("material") String material,
                               @RequestParam("blueprintOriginName") MultipartFile blueprintFile,
                               Model model, RedirectAttributes redirectAttributes) {

        Item newItem = Item.builder()
                .itemCode(itemCode)
                .itemName(itemName)
                .width(width)
                .length(length)
                .height(height)
                .material(material)
                .blueprintOriginName(blueprintFile.getOriginalFilename())
                .blueprintSaveName(blueprintFile.getName())
                .build();

        redirectAttributes.addFlashAttribute("unitCodes", itemService.getAllUnits());

        try {
            itemService.registerItem(newItem, blueprintFile);
        } catch (Exception e) {
            List<Unit> unitCodes = itemService.getAllUnits();
            model.addAttribute("unitCodes", unitCodes);
            model.addAttribute("errorMessage", "아이템 등록 중 오류가 발생했습니다.");
            return "item/register";
        }

        model.addAttribute("successMessage", "아이템이 성공적으로 등록되었습니다.");
        return "member/home";
    }
}
