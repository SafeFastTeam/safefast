package org.zerock.safefast.service.inventory_management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.inventory_management.InventoryValueDTO;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Quantity;
import org.zerock.safefast.entity.Receive;
import org.zerock.safefast.repository.QuantityRepository;
import org.zerock.safefast.repository.ReceiveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryManagementServiceImpl implements InventoryManagementService {

    private final QuantityRepository quantityRepository;
    private final ReceiveRepository receiveRepository;



    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByUnit() {
        List<Object[]> results = quantityRepository.findInventoryValueByUnit();
        return results.stream()
                .limit(6)
                .map(arr -> new InventoryValueDTO(
                        (String) arr[0],
                        (String) arr[0],
                        ((Number) arr[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByAssy() {
        List<Object[]> results = quantityRepository.findInventoryValueByAssy();
        return results.stream()
                .limit(6)
                .map(arr -> new InventoryValueDTO(
                        (String) arr[0],
                        (String) arr[0],
                        ((Number) arr[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryValueDTO> getTop6InventoryValueByPart() {
        List<Object[]> results = quantityRepository.findInventoryValueByPart();
        return results.stream()
                .limit(6)
                .map(arr -> new InventoryValueDTO(
                        (String) arr[0],
                        (String) arr[0],
                        ((Number) arr[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<InventoryValueDTO> getInventoryValuesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Receive> receives = receiveRepository.findByDateRange(startDate, endDate);

        return receives.stream()
                .map(receive -> {
                    Item item = receive.getItem();
                    Contract contract = item.getContracts().stream()
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("No contract found for item: " + item.getItemCode()));
                    String itemName = item.getItemName();
                    int receiveQuantity = receive.getReceiveQuantity();
                    double itemPrice = contract.getItemPrice();

                    Quantity quantity = quantityRepository.findByItem(item);
                    if (quantity == null) {
                        throw new IllegalStateException("No quantity found for item: " + item.getItemCode());
                    }
                    int allQuantity = quantity.getAllQuantity(); // 현재 재고 수량

                    return new InventoryValueDTO(item.getItemCode(), itemName, itemPrice * allQuantity, receiveQuantity, itemPrice, allQuantity);
                })
                .collect(Collectors.toList());
    }


}