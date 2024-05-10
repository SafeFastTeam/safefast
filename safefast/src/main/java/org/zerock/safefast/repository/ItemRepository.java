package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

}
