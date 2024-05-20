package org.zerock.safefast.service.receive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.safefast.dto.receive.UpdateReceiveQuantityDTO;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.Receive;
import org.zerock.safefast.repository.ProcurementPlanRepository;
import org.zerock.safefast.repository.ReceiveRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiveService {

    private final ReceiveRepository receiveRepository;
    private final ProcurementPlanRepository procurementPlanRepository;

    public List<ProcurementPlan> getAllProcurementPlan() {
        return procurementPlanRepository.findAll();
    }

    @Transactional
    public void updateStockFromReceive(List<UpdateReceiveQuantityDTO> receiveData) {
        for (UpdateReceiveQuantityDTO data : receiveData) {
            String procPlanNumber = data.getProcPlanNumber();
            int receivedQuantity = data.getReceiveQuantity();

            ProcurementPlan procurementPlan = procurementPlanRepository.findById(procPlanNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid procurement plan number: " + procPlanNumber));

            int currentStock = procurementPlan.getProcQuantity();

            // 기존 재고에 입고 수량을 더하여 재고를 업데이트
            procurementPlan.setProcQuantity(currentStock + receivedQuantity);

            // 변경된 재고를 데이터베이스에 저장하지 않고, 단순히 업데이트만 합니다.
            // 따라서 save 메서드를 호출하지 않습니다.
        }
    }

}
