package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
//    List<Contract> findByCoOpCompany(CoOpCompany coOpCompany);
}
