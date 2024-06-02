package org.zerock.safefast.service.inventory_management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.inventory_management.InventoryValueDTO;
import org.zerock.safefast.repository.QuantityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryManagementServiceImpl implements InventoryManagementService {

    private final QuantityRepository quantityRepository;

    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByUnit() {
        List<Object[]> results = quantityRepository.findTop6InventoryValueByUnit();
        return results.stream()
                .map(arr -> new InventoryValueDTO((String) arr[0], ((Number) arr[1]).doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByAssy() {
        List<Object[]> results = quantityRepository.findTop6InventoryValueByAssy();
        return results.stream()
                .map(arr -> new InventoryValueDTO((String) arr[0], ((Number) arr[1]).doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByPart() {
        List<Object[]> results = quantityRepository.findTop6InventoryValueByPart();
        return results.stream()
                .map(arr -> new InventoryValueDTO((String) arr[0], ((Number) arr[1]).doubleValue()))
                .collect(Collectors.toList());
    }
}