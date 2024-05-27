package org.zerock.safefast.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.zerock.safefast.repository.AssyRepository;
import org.zerock.safefast.repository.PartRepository;
import org.zerock.safefast.repository.UnitRepository;
import org.zerock.safefast.service.product.ItemService;


import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final UnitRepository unitRepository;
    private final AssyRepository assyRepository;
    private final PartRepository partRepository;

    @GetMapping("/register")
    public String showItemRegisterPage(Model model) {
        List<Unit> units = unitRepository.findAll();
        List<Assy> assies = assyRepository.findAll();
        List<Part> parts = partRepository.findAll();
        List<Item> items = itemService.getAllItems();
        model.addAttribute("units", units);
        model.addAttribute("assies", assies);
        model.addAttribute("parts", parts);
        model.addAttribute("items", items);
        return "item/register";
    }

    @PostMapping("/register")
    public String registerItem(@RequestParam("unitCode") String unitCode,
                               @RequestParam("assyCode") String assyCode,
                               @RequestParam("partCode") String partCode,
                               @RequestParam("itemName") String itemName,
                               @RequestParam("width") Integer width,
                               @RequestParam("length") Integer length,
                               @RequestParam("height") Integer height,
                               @RequestParam("material") String material,
                               @RequestParam("blueprintOriginName") MultipartFile blueprintFile) {

        Unit unit = unitRepository.findById(unitCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Unit Code: " + unitCode));
        Assy assy = assyRepository.findById(assyCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Assy Code: " + assyCode));
        Part part = partRepository.findById(partCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Part Code: " + partCode));

        Item item = Item.builder()
                .unit(unit)
                .assy(assy)
                .part(part)
                .itemName(itemName)
                .width(width)
                .length(length)
                .height(height)
                .material(material)
                .build();

        itemService.registerItem(item, blueprintFile);

        return "redirect:/item/register";
    }
}