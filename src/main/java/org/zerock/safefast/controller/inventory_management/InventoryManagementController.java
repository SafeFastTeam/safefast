package org.zerock.safefast.controller.inventory_management;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.safefast.dto.inventory_management.InventoryValueDTO;
import org.zerock.safefast.service.inventory_management.InventoryManagementService;

import java.util.List;

@Controller
@RequestMapping("/inventory_management")
@RequiredArgsConstructor
public class InventoryManagementController {

    private final InventoryManagementService inventoryManagementService;

    @GetMapping("/inventory_management")
    public String showInventoryManagement() {
        return "/inventory_management/inventory_management";
    }

    @GetMapping("/unit")
    @ResponseBody
    public List<InventoryValueDTO> getTop6InventoryValueByUnit() {
        return inventoryManagementService.getTop6InventoryValueByUnit();
    }

    @GetMapping("/assy")
    @ResponseBody
    public List<InventoryValueDTO> getTop6InventoryValueByAssy() {
        return inventoryManagementService.getTop6InventoryValueByAssy();
    }

    @GetMapping("/part")
    @ResponseBody
    public List<InventoryValueDTO> getTop6InventoryValueByPart() {
        return inventoryManagementService.getTop6InventoryValueByPart();
    }
}