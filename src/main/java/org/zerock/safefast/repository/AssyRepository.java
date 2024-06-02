package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Assy;

@Repository
public interface AssyRepository extends JpaRepository<Assy, String> {
    Assy findByAssyCode(String assyCode);
}
