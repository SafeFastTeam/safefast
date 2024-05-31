package org.zerock.safefast.service.purchase_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderRequest;
import org.zerock.safefast.dto.purchase_order.PurchaseOrderResponse;
import org.zerock.safefast.entity.*;
import org.zerock.safefast.repository.*;

import java.time.LocalDate;
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

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional
    public List<PurchaseOrder> createPurchaseOrders(List<PurchaseOrderRequest> purchaseOrderRequests) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRequests.stream().map(request -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchOrderNumber(generateNextPurchOrderNumber());
            purchaseOrder.setPurchOrderQuantity(request.getPurchOrderQuantity());
            purchaseOrder.setNote(request.getNote());
            purchaseOrder.setReceiveDuedate(request.getReceiveDuedate());
            purchaseOrder.setProcPlanNumber(request.getProcPlanNumber());

            purchaseOrder.setCoOpCompany(request.getCoOpCompany());
            purchaseOrder.setBusinessNumber(request.getCoOpCompany().getBusinessNumber());
            purchaseOrder.setItem(request.getItem());
            purchaseOrder.setItemCode(request.getItem().getItemCode());


            return purchaseOrder;
        }).toList();
        return purchaseOrderRepository.saveAll(purchaseOrders);
    }

    public Optional<ProcurementPlan> getProcurementPlanByNumber(String procPlanNumber) {
        return Optional.ofNullable(purchaseOrderRepository.findProcurementPlanByNumber(procPlanNumber));
    }

    //모든 발주서를 리스트업 합니다.
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public String generateNextPurchOrderNumber() {
        String maxPurchOrderNumber = purchaseOrderRepository.findMaxPurchOrderNumber();
        if (maxPurchOrderNumber == null) {
            return "ORD-001";
        }

        try {
            String[] parts = maxPurchOrderNumber.split("-");
            int nextNumber = Integer.parseInt(parts[1]) + 1;
            return String.format("ORD-%03d", nextNumber);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // 예외 발생 시 기본값 반환
            return "ORD-001";
        }
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
        response.setProcPlanNumber(purchaseOrder.getProcPlanNumber());
        response.setBusinessNumber(purchaseOrder.getBusinessNumber());
        response.setItemCode(purchaseOrder.getItemCode());
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
    @Transactional
    public void updatePurchaseOrder(String purchOrderNumber, PurchaseOrder modifiedOrder) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchOrderNumber)
                .orElseThrow(() -> new RuntimeException("발주서를 찾을 수 없습니다."));

        // 필요한 필드 업데이트
        purchaseOrder.setPurchOrderQuantity(modifiedOrder.getPurchOrderQuantity());
        purchaseOrder.setReceiveDuedate(modifiedOrder.getReceiveDuedate());
        purchaseOrder.setPurchOrderDate(modifiedOrder.getPurchOrderDate());
        // 기타 필드들도 업데이트

        purchaseOrderRepository.save(purchaseOrder);
    }
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    // 그래프를 그리기 위해, 상태별 엔티티의 수를 세는 메소드를 정의합니다.
    public long countTotalPlans(LocalDate startDate, LocalDate endDate) {
        return procurementPlanRepository.countByDateRange(startDate, endDate);
    }

    public long countIssuedOrders(LocalDate startDate, LocalDate endDate) {
        return purchaseOrderRepository.countByDateRange(startDate, endDate);
    }

    public long countProgressChecks(LocalDate startDate, LocalDate endDate) {
        return progressCheckItemRepository.countByDateRange(startDate, endDate);
    }

    public long countCompletedProcurements(LocalDate startDate, LocalDate endDate) {
        return receiveRepository.countByDateRange(startDate, endDate);
    }

    /*
     *      진척도를 계산하는 서비스 메서드입니다. no usages라고 표시되는 에러가 있습니다만,
     *      이 메서드를 삭제하면 template parsing 에러가 발생합니다.
     */
    public int calculateProgressForPurchaseOrder(PurchaseOrder purchaseOrder) {
        List<ProgressCheckItem> progressCheckItems = purchaseOrder.getProgressCheckItems();
        int totalCompletedQuantity = progressCheckItems.stream()
                .mapToInt(ProgressCheckItem::getCompletedQuantity)
                .sum();
        int progress = (int) ((double) totalCompletedQuantity / purchaseOrder.getPurchOrderQuantity() * 100);
        return progress;
    }
}
