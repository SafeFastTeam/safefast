package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByUnitCode(String unitCode); // 특정 unit에 속하는 아이템을 조회하는 메서드
    List<Item> findByPartCode(String partCode); // 특정 part를 사용하는 아이템을 조회하는 메서드
    List<Item> findByAssyCode(String assyCode); // 특정 assy에 속하는 아이템을 조회하는 메서드
}
