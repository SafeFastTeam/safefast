package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Releases;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Integer> {
}
