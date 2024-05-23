package org.zerock.safefast.service.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.ProgressCheckItem;
import org.zerock.safefast.repository.ProgressCheckItemRepository;

import java.util.List;

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

}
