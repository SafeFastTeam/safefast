package org.zerock.safefast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    @Query("SELECT MAX(c.contractNumber) FROM Contract c")
    String findMaxContractNumber();

}
