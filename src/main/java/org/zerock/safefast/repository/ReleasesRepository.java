package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.entity.Releases;

import java.util.List;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Integer> {
//    List<Releases> findByItemCode(String itemCode);

    // 해당 아이템에 대한 총 출고 수량을 조회하는 메서드
    @Query("SELECT SUM(r.releaseQuantity) FROM Releases r WHERE r.item = :itemCode")
    Integer sumQuantityByItem(String itemCode);
}

