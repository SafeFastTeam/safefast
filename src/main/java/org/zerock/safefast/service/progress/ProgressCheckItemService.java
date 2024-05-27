package org.zerock.safefast.service.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.repository.ProgressCheckItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressCheckItemService {

    private final ProgressCheckItemRepository progressCheckItemRepository;

    @Autowired
    public ProgressCheckItemService(ProgressCheckItemRepository progressCheckItemRepository) {
        this.progressCheckItemRepository = progressCheckItemRepository;
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

}
