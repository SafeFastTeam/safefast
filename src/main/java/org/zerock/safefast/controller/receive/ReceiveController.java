package org.zerock.safefast.controller.receive;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.dto.inventory_management.InventoryValueDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.dto.receive.ReleasesDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.QuantityRepository;
import org.zerock.safefast.service.inventory_management.InventoryManagementService;
import org.zerock.safefast.service.inventory_management.InventoryManagementServiceImpl;
import org.zerock.safefast.service.receive.ReceiveService;
import org.zerock.safefast.service.releases.ReleasesService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/receive")
@RequiredArgsConstructor
@Log4j2
public class ReceiveController {

    private final ReceiveService receiveService;
    private final ReleasesService releasesService;
    private final ItemRepository itemRepository;
    private final QuantityRepository quantityRepository;
    private final InventoryManagementService inventoryManagementService;
    private final InventoryManagementServiceImpl inventoryManagementServiceImpl;

    @GetMapping("/receive")
    public String showReceivePage(PageRequestDTO pageRequestDTO, Model model) {
        List<PurchaseOrder> purchaseOrderList = receiveService.getAllPurchaseOrder();
        List<Quantity> quantityList = receiveService.getAllQuantity();
        List<Releases> releasesList = receiveService.getAllReleases();
        List<Receive> receiveList = receiveService.getAllReceive();
        PageResultDTO<PurchaseOrder, PurchaseOrder> resultA = receiveService.getListA(pageRequestDTO);
        PageResultDTO<Receive, Receive> resultB= receiveService.getListB(pageRequestDTO);
        PageResultDTO<Quantity, Quantity> resultC= releasesService.getListA(pageRequestDTO);
        PageResultDTO<Releases, Releases> resultD= releasesService.getListB(pageRequestDTO);
        model.addAttribute("purchases", purchaseOrderList);
        model.addAttribute("quantities", quantityList);
        model.addAttribute("releases", releasesList);
        model.addAttribute("receives", receiveList);
        model.addAttribute("resultA", resultA);
        model.addAttribute("resultB", resultB);
        model.addAttribute("resultC", resultC);
        model.addAttribute("resultD", resultD);
        return "/receive/receive";
    }

    @PostMapping("/receive")
    public ResponseEntity<String> addReceive(@RequestBody ReceiveDTO receiveDTO) {
        log.info("Received DTO: {}", receiveDTO);
        try {
            receiveService.addReceive(receiveDTO);
            return ResponseEntity.ok("{\"message\": \"등록되었습니다.\"}");
        } catch (Exception e) {
            log.error("Error while adding receive: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"등록에 실패했습니다.\"}");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRelease(@RequestBody ReleasesDTO releasesDTO) {
        log.info("Received DTO: {}", releasesDTO);
        try {
            releasesService.addRelease(releasesDTO);
            return ResponseEntity.ok("{\"message\": \"등록되었습니다.\"}");
        } catch (Exception e) {
            log.error("Error while adding receive: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"등록에 실패했습니다.\"}");
        }
    }



//    @PostMapping("/add")
//    public String addRelease(@RequestParam("releaseQuantity") Integer releaseQuantity,
//                             @RequestParam("itemCode") String itemCode,
//                             @RequestParam("quantityId") Integer quantityId) {
//
//        Item item = itemRepository.findById(itemCode)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Item: " + itemCode));
//
//        Quantity quantity = quantityRepository.findById(quantityId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid quantityId: " + quantityId));
//
//        Releases releases = Releases.builder()
//                .item(item)
//                .releaseQuantity(releaseQuantity)
//                .quantity(quantity)
//                .build();
//
//        releasesService.registerReleases(releases);
//
//        return "receive/receive";
//    }
    @GetMapping("/search")
    public String searchInventoryValuesByDateRange(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "category", required = false) String category,
            Model model) {

        LocalDate startDate = startDateStr != null ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = endDateStr != null ? LocalDate.parse(endDateStr) : null;

        List<InventoryValueDTO> inventoryValues;

        if (category == null || category.equalsIgnoreCase("unit")) {
            inventoryValues = inventoryManagementService.getTop6InventoryValueByUnit();
        } else if (category.equalsIgnoreCase("assy")) {
            inventoryValues = inventoryManagementService.getTop6InventoryValueByAssy();
        } else if (category.equalsIgnoreCase("part")) {
            inventoryValues = inventoryManagementService.getTop6InventoryValueByPart();
        } else {
            // 잘못된 카테고리인 경우 처리
            inventoryValues = Collections.emptyList();
        }

        // 기간 검색 조건이 있는 경우
        if (startDate != null && endDate != null) {
            List<Receive> receives = receiveService.getReceivesByDateRange(startDate, endDate);
            // receives 데이터를 사용하여 inventoryValues 계산
            // ...
        }

        model.addAttribute("inventoryValues", inventoryValues);
        return "/inventory_management/inventory_management";
    }
    @GetMapping("/inventory_management/search")
    public ResponseEntity<List<InventoryValueDTO>> searchInventoryValuesByDateRange(
            @RequestParam(value = "startDate") String startDateStr,
            @RequestParam(value = "endDate") String endDateStr) {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<InventoryValueDTO> inventoryValues = inventoryManagementServiceImpl.getInventoryValuesByDateRange(startDate, endDate);

        return ResponseEntity.ok(inventoryValues);
    }
}