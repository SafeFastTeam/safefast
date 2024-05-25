package org.zerock.safefast.service.purchase_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderRequest;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.repository.ProcurementPlanRepository;
import org.zerock.safefast.repository.ProgressCheckItemRepository;
import org.zerock.safefast.repository.PurchaseOrderRepository;
import org.zerock.safefast.repository.ReceiveRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProcurementPlanRepository procurementPlanRepository;

    //해당 레포지토리들은 그래프 그리기 위해 선언되었습니다.
    @Autowired
    private ProgressCheckItemRepository progressCheckItemRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

    public List<PurchaseOrder> createPurchaseOrders(List<PurchaseOrderRequest> purchaseOrderRequests) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        List<PurchaseOrder> purchaseOrders = purchaseOrderRequests.stream().map(request -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setProcPlanNumber(request.procPlanNumber);
            purchaseOrder.setPurchOrderQuantity(request.purchaseOrderQuantity);
            purchaseOrder.setReceiveDuedate(LocalDateTime.parse(request.receiveDuedate, formatter));
            return purchaseOrder;
        }).collect(Collectors.toList());

        return purchaseOrderRepository.saveAll(purchaseOrders);
    }

    public Optional<ProcurementPlan> getProcurementPlanByNumber(String procPlanNumber) {
        return procurementPlanRepository.findByProcPlanNumber(procPlanNumber);
    }

    //모든 발주서를 리스트업 합니다.
    public List<PurchaseOrder> getAllPurchaseOrders() {

        return purchaseOrderRepository.findAll();
    }

    public List<PurchaseOrderResponse> getPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        // PurchaseOrder를 PurchaseOrderResponse로 변환하여 리스트로 반환
        return purchaseOrders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderResponse> getPurchaseOrderDetails(List<String> orderNumbers) {
        // 체크된 발주에 대한 상세 정보를 가져오는 로직을 작성하세요.
        // orderNumbers에 있는 발주번호 리스트를 기준으로 PurchaseOrder 엔티티를 조회하고,
        // 필요한 정보를 PurchaseOrderResponse로 변환하여 반환합니다.

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByPurchOrderNumberIn(orderNumbers);

        return purchaseOrders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // PurchaseOrder를 PurchaseOrderResponse로 매핑하는 메서드
    private PurchaseOrderResponse mapToResponse(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setPurchOrderNumber(purchaseOrder.getPurchOrderNumber());
        response.setPurchOrderDate(String.valueOf(purchaseOrder.getPurchOrderDate()));
        response.setReceiveDuedate(String.valueOf(purchaseOrder.getReceiveDuedate()));
        response.setPurchOrderQuantity(purchaseOrder.getPurchOrderQuantity());
        // 나머지 필드들도 필요에 따라 매핑

        return response;
    }

    public PurchaseOrderResponse getPurchaseOrderDetails(String purchOrderNumber) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByPurchOrderNumber(purchOrderNumber);
            if (purchaseOrder == null) {
                throw new IllegalArgumentException("Purchase order not found for purchOrderNumber: " + purchOrderNumber);
            }
            return new PurchaseOrderResponse(purchaseOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new PurchaseOrderResponse();  // 기본 생성자 사용
        }
    }


    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    // 그래프를 그리기 위해, 상태별 엔티티의 수를 세는 메소드를 정의합니다.
    public long countTotalPlans(LocalDateTime startDate, LocalDateTime endDate) {
        return procurementPlanRepository.countByDateRange(startDate, endDate);
    }

    public long countIssuedOrders(LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseOrderRepository.countByDateRange(startDate, endDate);
    }

    public long countProgressChecks(LocalDateTime startDate, LocalDateTime endDate) {
        return progressCheckItemRepository.countByDateRange(startDate, endDate);
    }

    public long countCompletedProcurements(LocalDateTime startDate, LocalDateTime endDate) {
        return receiveRepository.countByDateRange(startDate, endDate);
    }
}
