package com.example.groceries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.groceries.Entity.Item;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
    
    Optional<Item> findItemByName(String name);
}
