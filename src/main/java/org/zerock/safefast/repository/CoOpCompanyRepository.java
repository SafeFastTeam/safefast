package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.CoOpCompany;

import java.util.List;

@Repository
public interface CoOpCompanyRepository extends JpaRepository<CoOpCompany, String> {

}
