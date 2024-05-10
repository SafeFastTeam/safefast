package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Receive;

@Repository
public interface ReceiveRepository extends JpaRepository<Receive, Integer> {
}
