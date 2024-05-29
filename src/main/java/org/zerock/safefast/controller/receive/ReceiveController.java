package org.zerock.safefast.controller.receive;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.QuantityRepository;
import org.zerock.safefast.service.receive.ReceiveService;
import org.zerock.safefast.service.releases.ReleasesService;

import java.time.LocalDate;
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

    @GetMapping("/receive")
    public String showReceivePage(Model model) {
        List<PurchaseOrder> purchaseOrderList = receiveService.getAllPurchaseOrder();
        List<ProgressCheckItem> progressCheckItemList = receiveService.getAllProgressCheckItem();
        List<Quantity> quantityList = receiveService.getAllQuantity();
        List<Releases> releasesList = receiveService.getAllReleases();
        model.addAttribute("purchases", purchaseOrderList);
        model.addAttribute("quantities", quantityList);
        model.addAttribute("releases", releasesList);
        model.addAttribute("progressCheckItems", progressCheckItemList);
        return "receive/receive";
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
    public String addRelease(@RequestParam("releaseQuantity") Integer releaseQuantity,
                             @RequestParam("itemCode") String itemCode,
                             @RequestParam("quantityId") Integer quantityId) {

        Item item = itemRepository.findById(itemCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Item: " + itemCode));

        Quantity quantity = quantityRepository.findById(quantityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quantityId: " + quantityId));

        Releases releases = Releases.builder()
                .item(item)
                .releaseQuantity(releaseQuantity)
                .quantity(quantity)
                .build();

        releasesService.registerReleases(releases);

        return "receive/receive";
    }

}