package org.zerock.safefast.service.receive;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.Receive;
import org.zerock.safefast.repository.ProcurementPlanRepository;
import org.zerock.safefast.repository.ReceiveRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReceiveService {

    private final ReceiveRepository receiveRepository;
    private final ProcurementPlanRepository procurementPlanRepository;

    public List<ProcurementPlan> getAllProcurementPlan() {
        return procurementPlanRepository.findAll();
    }

    public void addReceive(ReceiveDTO receiveDTO) {
        log.info("addReceive called with DTO: {}", receiveDTO);

        Receive receive = new Receive();
        receive.setReceiveDate(LocalDateTime.now());
        receive.setReceiveQuantity(receiveDTO.getReceiveQuantity());
        receive.setReceiveNumber(receiveDTO.getReceiveNumber()); // ReceiveNumber 설정 추가

        String procPlanNumber = receiveDTO.getProcPlanNumber();
        Optional<ProcurementPlan> optionalProcurementPlan = procurementPlanRepository.findByProcPlanNumber(procPlanNumber);

        if (optionalProcurementPlan.isPresent()) {
            log.info("ProcurementPlan found: {}", optionalProcurementPlan.get());
            receive.setProcurementPlan(optionalProcurementPlan.get());
        } else {
            log.warn("ProcurementPlan not found for procPlanNumber: {}", procPlanNumber);
            throw new IllegalArgumentException("Invalid procPlanNumber: " + procPlanNumber);
        }

        receiveRepository.save(receive);
        log.info("Receive entity saved successfully");
    }
}


