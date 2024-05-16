package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.safefast.entity.ProgressCheckItem;

public interface ProgressCheckItemRepository extends JpaRepository<ProgressCheckItem, Integer> {
}
