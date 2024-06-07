package org.zerock.safefast.service.inventory_management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.inventory_management.InventoryValueDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public interface InventoryManagementService {
    List<InventoryValueDTO> getTop6InventoryValueByUnit();

    List<InventoryValueDTO> getTop6InventoryValueByAssy();

    List<InventoryValueDTO> getTop6InventoryValueByPart();

    List<InventoryValueDTO> getInventoryValuesByDateRange(LocalDate startDate, LocalDate endDate);

}