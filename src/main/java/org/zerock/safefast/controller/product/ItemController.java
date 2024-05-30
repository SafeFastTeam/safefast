package org.zerock.safefast.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.AssyRepository;
import org.zerock.safefast.repository.PartRepository;
import org.zerock.safefast.repository.UnitRepository;
import org.zerock.safefast.service.product.ItemService;


import java.io.IOException;
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



    // 페이징 처리된 결과 받아오도록 수정

    @GetMapping("/register")
    public String showItemRegisterPage(PageRequestDTO pageRequestDTO, Model model) {
        List<Unit> units = unitRepository.findAll();
        List<Assy> assies = assyRepository.findAll();
        List<Part> parts = partRepository.findAll();
        // 페이징 처리된 결과를 받아옴
        PageResultDTO<ItemDTO, Item> result = itemService.getList(pageRequestDTO);
        model.addAttribute("units", units);
        model.addAttribute("assies", assies);
        model.addAttribute("parts", parts);
        // 페이징 처리된 결과를 모델에 추가
        model.addAttribute("result", result);
        // 현재 페이지와 전체 페이지 정보를 모델에 추가
        model.addAttribute("currentPage", result.getPage());
        model.addAttribute("totalPages", result.getTotalPage());
        return "item/register";
    }

    // 아래 거가 원본
/*    @GetMapping("/register")
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
    }*/

    @PostMapping("/register")
    public String registerItem(@RequestParam("unitCode") String unitCode,
                               @RequestParam("assyCode") String assyCode,
                               @RequestParam("partCode") String partCode,
                               @RequestParam("itemName") String itemName,
                               @RequestParam("width") Integer width,
                               @RequestParam("length") Integer length,
                               @RequestParam("height") Integer height,
                               @RequestParam("material") String material,
                               @RequestParam("blueprintOriginName") MultipartFile blueprintFile,
                               PageRequestDTO pageRequestDTO, Model model) {

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

        // 등록 후 페이지를 다시 조회하여 최신 데이터를 반영
        PageResultDTO<ItemDTO, Item> result = itemService.getList(pageRequestDTO);
        List<Unit> units = unitRepository.findAll();
        List<Assy> assies = assyRepository.findAll();
        List<Part> parts = partRepository.findAll();
        model.addAttribute("result", result); // 페이징 결과를 모델에 담아서 전달
        model.addAttribute("units", units);
        model.addAttribute("assies", assies);
        model.addAttribute("parts", parts);

        return "redirect:/item/register";
    }

    @GetMapping("/search")
    public ResponseEntity<PageResultDTO<ItemDTO, Item>> searchItems(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).build();
        PageResultDTO<ItemDTO, Item> result = itemService.searchItems(pageRequestDTO, keyword);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/item/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Resource resource = itemService.loadFileAsResource(fileName);
        return ResponseEntity.ok().body(resource);
    }


}