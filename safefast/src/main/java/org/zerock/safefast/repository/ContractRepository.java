package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

}
