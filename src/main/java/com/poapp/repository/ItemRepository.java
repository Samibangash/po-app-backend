package com.poapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poapp.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
