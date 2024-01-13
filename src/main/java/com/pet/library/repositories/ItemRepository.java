package com.pet.library.repositories;

import com.pet.library.entities.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<LibraryItem, Long> {

}