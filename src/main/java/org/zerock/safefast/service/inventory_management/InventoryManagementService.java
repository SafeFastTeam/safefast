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

//    public Map<String, Object> getTop6Items(String category) {
//        List<Object[]> result;
//        switch (category) {
//            case "unit":
//                result = quantityRepository.findTop6Units();
//                break;
//            case "assy":
//                result = quantityRepository.findTop6Assys();
//                break;
//            case "part":
//                result = quantityRepository.findTop6Parts();
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid category: " + category);
//        }
//
//        Map<String, Object> data = new HashMap<>();
//        String[] labels = new String[result.size()];
//        Long[] values = new Long[result.size()];
//
//        for (int i = 0; i < result.size(); i++) {
//            labels[i] = (String) result.get(i)[0];
//            values[i] = (Long) result.get(i)[1];
//        }
//
//        data.put("labels", labels);
//        data.put("data", values);
//
//        return data;
//    }
}