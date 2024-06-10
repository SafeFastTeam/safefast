package org.zerock.safefast.service.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.item.ItemDTO;
import org.zerock.safefast.dto.page.PageRequestDTO;
import org.zerock.safefast.dto.page.PageResultDTO;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.repository.ProgressCheckItemRepository;
import org.zerock.safefast.repository.PurchaseOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProgressCheckItemService {

    private final ProgressCheckItemRepository progressCheckItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public ProgressCheckItemService(ProgressCheckItemRepository progressCheckItemRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.progressCheckItemRepository = progressCheckItemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public void saveProgressCheckItems(List<ProgressCheckItem> progressCheckItems) {
        progressCheckItemRepository.saveAll(progressCheckItems);
    }

    public void saveOrUpdateProgressCheckItems(List<ProgressCheckItem> progressCheckItems) {
        for (ProgressCheckItem item : progressCheckItems) {
            Optional<ProgressCheckItem> existingItem = progressCheckItemRepository
                    .findByPurchOrderNumberAndProgCheckOrder(item.getPurchaseOrder().getPurchOrderNumber(), item.getProgCheckOrder());

            if (existingItem.isPresent()) {
                ProgressCheckItem updateItem = existingItem.get();
                updateItem.setProgCheckDate(item.getProgCheckDate());
                updateItem.setCompletedQuantity(item.getCompletedQuantity());
                updateItem.setProgCheckResult(item.getProgCheckResult());
                updateItem.setSupplementation(item.getSupplementation());
                progressCheckItemRepository.save(updateItem);
            } else {
                progressCheckItemRepository.save(item);
            }
        }
    }

    public List<ProgressCheckItem> getProgressCheckItemsByPurchOrderNumber(String purchOrderNumber) {
        return progressCheckItemRepository.findByPurchOrderNumber(purchOrderNumber);
    }

    public PageResultDTO<PurchaseOrder, PurchaseOrder> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("purchOrderNumber").descending());
        Page<PurchaseOrder> result = purchaseOrderRepository.findAll(pageable);
        Function<PurchaseOrder, PurchaseOrder> fn = Function.identity();
        return new PageResultDTO<>(result, fn);
    }

}
