package org.zerock.safefast.service.receive;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReceiveService {

    private final ReceiveRepository receiveRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final QuantityRepository quantityRepository;
    private final ProductionPlanRepository productionPlanRepository;
    private final ReleasesRepository releasesRepository;
    private final ProgressCheckItemRepository progressCheckItemRepository;

    public void addReceive(ReceiveDTO receiveDTO) {
        log.info("addReceive called with DTO: {}", receiveDTO);

        // PurchaseOrder 조회
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(receiveDTO.getPurchOrderNumber())
                .orElseThrow(() -> new EntityNotFoundException("발주 번호를 찾을 수 없습니다."));


        // Receive 엔티티 생성 및 설정
        Receive receive = new Receive();
        receive.setReceiveDate(LocalDate.now());
        receive.setReceiveQuantity(receiveDTO.getReceiveQuantity());
        receive.setPurchaseOrder(purchaseOrder);
        receive.setItem(purchaseOrder.getItem());

        // Receive 저장
        receiveRepository.save(receive);
        log.info("Receive entity saved successfully: {}", receive);

        // Quantity 엔티티 업데이트
        updateQuantity(receive.getItem(), receive.getReceiveQuantity());
    }

    // Quantity 엔티티 업데이트 메서드 추가
    private void updateQuantity(Item item, int quantity) {
        Quantity quantityEntity = quantityRepository.findByItem(item);
        if (quantityEntity == null) {
            // Quantity 엔티티가 없는 경우 새로 생성
            quantityEntity = new Quantity();
            quantityEntity.setItem(item);
            quantityEntity.setAllQuantity(quantity);
        } else {
            // Quantity 엔티티가 있는 경우 수량 업데이트
            quantityEntity.setAllQuantity(quantityEntity.getAllQuantity() + quantity);
        }
        quantityRepository.save(quantityEntity);
        log.info("Quantity entity updated successfully: {}", quantityEntity);
    }


    public List<PurchaseOrder> getAllPurchaseOrder() {
        return purchaseOrderRepository.findAll();
    }


    public List<Quantity> getAllQuantity() {
        return quantityRepository.findAll();
    }

    public List<Releases> getAllReleases() {
        return releasesRepository.findAll();
    }

    public List<ProgressCheckItem> getAllProgressCheckItem() {
        return progressCheckItemRepository.findAll();
    }

    public List<Receive> getReceivesByDateRange(LocalDate startDate, LocalDate endDate) {
        return receiveRepository.findByDateRange(startDate, endDate);
    }

    public List<Receive> getAllReceive() {
        return receiveRepository.findAll();
    }


}
