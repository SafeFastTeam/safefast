package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.ProcurementPlan;
import org.zerock.safefast.entity.Receive;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiveRepository extends JpaRepository<Receive, Integer> {
    Optional<Receive> findByProcurementPlan(ProcurementPlan procurementPlan);
}
