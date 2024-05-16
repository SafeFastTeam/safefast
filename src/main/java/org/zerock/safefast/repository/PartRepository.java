package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, String> {
}
