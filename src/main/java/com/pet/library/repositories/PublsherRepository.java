package com.pet.library.repositories;

import com.pet.library.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublsherRepository extends JpaRepository<Publisher, Long> {
}
