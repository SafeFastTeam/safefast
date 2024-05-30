package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByEmpNumber(String empNumber);

    Member findByEmail(String email);

    Member findByEmpNumberAndEmail(String empNumber, String email);

    Member findByNameAndEmail(String name, String email);

}
