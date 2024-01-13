package com.pet.library.services;

import com.pet.library.entities.LibraryCardRecord;
import com.pet.library.entities.LibraryItem;
import com.pet.library.entities.User;
import com.pet.library.repositories.ItemRepository;
import com.pet.library.repositories.LibraryCardRecordRepository;
import com.pet.library.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class LibraryCardService {


    private final LibraryCardRecordRepository libraryCardRecordRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EntityManager entityManager;

    @Autowired
    public LibraryCardService(LibraryCardRecordRepository libraryCardRecordRepository, UserRepository userRepository, ItemRepository itemRepository, EntityManager entityManager) {
        this.libraryCardRecordRepository = libraryCardRecordRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public LibraryCardRecord giveItemToUser(Long userId, Long itemId, Long issuerId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User isn't available");
        }

        Optional<LibraryItem> itemOptional = itemRepository.findById(itemId);
        LibraryItem item = itemOptional.orElse(null);

        if (item == null || item.getQuantity().equals(0)) {
            throw new IllegalArgumentException("Item isn't available");
        }

        LibraryCardRecord libraryCardRecord = new LibraryCardRecord();
        libraryCardRecord.setIssuedTime(Instant.now());
        libraryCardRecord.setUser(entityManager.getReference(User.class, userId));
        libraryCardRecord.setLibraryItem(entityManager.getReference(LibraryItem.class, itemId));
        libraryCardRecord.setLibrarianIssued(entityManager.getReference(User.class, issuerId));

        //TODO Кол-во книжек должно убавляться
        item.setQuantity(item.getQuantity() - 1);
        libraryCardRecordRepository.save(libraryCardRecord);
        return libraryCardRecord;
    }


    @Transactional
    public LibraryCardRecord returnItemFromUser(Long userId, Long recordId, Long receiverId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User isn't available");
        }

        Optional<LibraryCardRecord> recordOptional = libraryCardRecordRepository.findById(recordId);
        LibraryCardRecord record = recordOptional.orElse(null);

        if (record == null) {
            throw new IllegalArgumentException("Record isn't available");
        }
        //TODO Кол-во книжек должно увеличиваться

        record.setReturnedTime(Instant.now());
        record.setLibrarianReceiver(entityManager.getReference(User.class, receiverId));

        record.getLibraryItem().setQuantity(record.getLibraryItem().getQuantity() - 1);
        libraryCardRecordRepository.save(record);

        return record;
    }
}
