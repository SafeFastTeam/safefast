package org.zerock.safefast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.InventoryItem;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByItem_ItemCodeContaining(String keyword);
    List<InventoryItem> findByItem_ItemNameContaining(String keyword);
    List<InventoryItem> findByItem_MaterialContaining(String keyword);
    Page<InventoryItem> findAll(Pageable pageable);
}
