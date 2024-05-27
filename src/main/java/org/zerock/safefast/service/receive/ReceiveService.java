package org.zerock.safefast.service.receive;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.receive.ReceiveDTO;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.entity.Receive;
import org.zerock.safefast.repository.ItemRepository;
import org.zerock.safefast.repository.PurchaseOrderRepository;
import org.zerock.safefast.repository.ReceiveRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReceiveService {

    private final ReceiveRepository receiveRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ItemRepository itemRepository;

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
    }

    public List<PurchaseOrder> getAllPurchaseOrder() {
        return purchaseOrderRepository.findAll();
    }


    public List<Receive> getAllReceive() {
        return receiveRepository.findAll();
    }
}
