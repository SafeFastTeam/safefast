package org.zerock.safefast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Query("SELECT MAX(i.itemCode) FROM Item i WHERE i.unit.unitCode = :unitCode AND i.assy.assyCode = :assyCode AND i.part.partCode = :partCode")
    String findMaxItemCode(String unitCode, String assyCode, String partCode);

    Page<Item> findByItemNameContaining(String keyword, Pageable pageable);

}
