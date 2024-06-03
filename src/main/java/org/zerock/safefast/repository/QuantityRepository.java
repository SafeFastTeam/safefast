package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Quantity;

import java.util.List;

public interface QuantityRepository extends JpaRepository <Quantity, Integer> {
    Quantity findByItem(Item item);

    // 대분류 상위 6개 재고금액 조회
    @Query("SELECT q.item.unit.unitName, SUM(q.allQuantity * c.itemPrice) AS totalInventoryValue " +
            "FROM Quantity q " +
            "JOIN q.item.contract c " +
            "GROUP BY q.item.unit.unitName " +
            "ORDER BY totalInventoryValue DESC")
    List<Object[]> findInventoryValueByUnit();

    // 중분류 상위 6개 재고금액 조회
    @Query("SELECT q.item.assy.assyName, SUM(q.allQuantity * c.itemPrice) AS totalInventoryValue " +
            "FROM Quantity q " +
            "JOIN q.item.contract c " +
            "GROUP BY q.item.assy.assyName " +
            "ORDER BY totalInventoryValue DESC")
    List<Object[]> findInventoryValueByAssy();

    // 소분류 상위 6개 재고금액 조회
    @Query("SELECT q.item.part.partName, SUM(q.allQuantity * c.itemPrice) AS totalInventoryValue " +
            "FROM Quantity q " +
            "JOIN q.item.contract c " +
            "GROUP BY q.item.part.partName " +
            "ORDER BY totalInventoryValue DESC")
    List<Object[]> findInventoryValueByPart();
}