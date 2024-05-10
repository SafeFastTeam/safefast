package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

}
