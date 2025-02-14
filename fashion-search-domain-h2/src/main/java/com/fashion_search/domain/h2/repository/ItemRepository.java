package com.fashion_search.domain.h2.repository;

import com.fashion_search.domain.h2.entities.ItemEntity;
import com.fashion_search.domain.h2.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByCategoryType(CategoryType categoryType);
}
