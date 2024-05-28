package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Quantity;

public interface QuantityRepository extends JpaRepository <Quantity, Integer> {
    Quantity findByItem(Item item);
}
