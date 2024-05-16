package org.zerock.safefast.service.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.repository.ProgressCheckItemRepository;

@Service
@RequiredArgsConstructor
public class ProgressCheckItemService {

    private final ProgressCheckItemRepository progressCheckItemRepository;

}
