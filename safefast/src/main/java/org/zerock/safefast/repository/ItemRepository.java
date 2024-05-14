package org.zerock.safefast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.safefast.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

}
