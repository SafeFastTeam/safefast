package org.zerock.safefast.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/item")
public class ItemController {

    @Value("${file.upload-dir}")
    private String uploadDir;

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
                .blueprintOriginName(blueprintFile.getOriginalFilename())
                .blueprintSaveName(blueprintFile.getOriginalFilename())
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

    @GetMapping("/file/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found " + fileName);
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file " + fileName, ex);
        }
    }

    private String encodeFileName(String fileName) {
        try {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode file name", e);
        }
    }

}