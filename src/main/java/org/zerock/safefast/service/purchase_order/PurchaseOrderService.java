package org.zerock.safefast.service.purchase_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderRequest;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.PurchaseOrder;
import org.zerock.safefast.repository.ProcurementPlanRepository;
import org.zerock.safefast.repository.PurchaseOrderRepository;

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

}
