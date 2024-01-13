package com.pet.library.repositories;

import com.pet.library.entities.LibraryCardRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryCardRecordRepository extends JpaRepository<LibraryCardRecord, Long> {

    public LibraryCardRecord findByUserId(Long userId);

}
