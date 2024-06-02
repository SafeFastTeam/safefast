package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {
    Unit findByUnitCode(String unitCode);
}
