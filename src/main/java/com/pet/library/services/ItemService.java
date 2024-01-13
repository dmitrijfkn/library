package com.pet.library.services;

import com.pet.library.dtos.LibraryItemDTO;
import com.pet.library.entities.*;
import com.pet.library.repositories.ItemRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final PublisherService publisherService;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final ItemTypeService itemTypeService;

    private final EntityManager entityManager;

    @Autowired
    public ItemService(ItemRepository itemRepository, PublisherService publisherService, AuthorService authorService, CategoryService categoryService, ItemTypeService itemTypeService, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.itemTypeService = itemTypeService;
        this.entityManager = entityManager;
    }

    public LibraryItem save(LibraryItemDTO libraryItemDTO) {

        LibraryItem libraryItem = libraryItemDtoToLibraryItem(libraryItemDTO);

        return itemRepository.save(libraryItem);
    }

    private LibraryItem libraryItemDtoToLibraryItem(LibraryItemDTO libraryItemDTO) {

        LibraryItem libraryItem = new LibraryItem();

        libraryItem.setId(libraryItemDTO.getId());
        libraryItem.setTitle(libraryItemDTO.getTitle());
        libraryItem.setYearOfPublication(libraryItemDTO.getYearOfPublication());
        libraryItem.setBibliographicIndex(libraryItemDTO.getBibliographicIndex());
        libraryItem.setAdded(Instant.now());
        libraryItem.setQuantity(libraryItemDTO.getQuantity());

        //TODO Remove Attach, make it cascade


        libraryItem.setPublisher(entityManager.getReference(Publisher.class, libraryItemDTO.getPublisherId()));
        attachAuthors(libraryItem, libraryItemDTO.getAuthorIds());
        libraryItem.setCategories(categoryService.findAllById(libraryItemDTO.getCategoryIds()));
        libraryItem.setType(entityManager.getReference(ItemType.class, libraryItemDTO.getTypeId()));

        //   attachPublisher(libraryItem, publisherService.findById(libraryItemDTO.getPublisherId()));
        //   attachAuthors(libraryItem, libraryItemDTO.getAuthorIds());
        //   attachCategories(libraryItem, categoryService.findAllById(libraryItemDTO.getCategoryIds()));
        //   attachItemType(libraryItem, itemTypeService.findById(libraryItemDTO.getTypeId()));

        return libraryItem;
    }

    private void attachPublisher(LibraryItem libraryItem, Publisher publisher) {
        if (libraryItem == null || publisher == null) {
            throw new IllegalArgumentException();
        }

        libraryItem.setPublisher(publisher);
        publisher.getLibraryItems().add(libraryItem);
    }

    private void attachAuthors(LibraryItem libraryItem, Map<Long, Integer> authorIds) {
        //  Set<Author> authors = authorService.findAllById(authorIds.keySet());

        if (libraryItem == null) {
            throw new IllegalArgumentException();
        }
        if (authorIds == null || authorIds.isEmpty()) {
            return;
        }

        if(libraryItem.getAuthors()==null){
            libraryItem.setAuthors(new HashSet<>());
        }

        //    for (Author author : authors) {
        for (Map.Entry<Long, Integer> entry : authorIds.entrySet()) {
            ItemAuthor itemAuthor = new ItemAuthor();

            itemAuthor.setAuthor(entityManager.getReference(Author.class, entry.getKey()));
            itemAuthor.setLibraryItem(libraryItem);
            //  itemAuthor.setAuthor(author);
            //  itemAuthor.setLibraryItem(libraryItem);
              libraryItem.getAuthors().add(itemAuthor);
            //  author.getItems().add(itemAuthor);

            itemAuthor.setAuthorOrder(entry.getValue());
        }
    }


    private void attachCategories(LibraryItem libraryItem, Set<Category> categories) {
        for (Category category : categories) {
            libraryItem.getCategories().add(category);
            category.getLibraryItems().add(libraryItem);
        }
    }


    private void attachItemType(LibraryItem libraryItem, ItemType itemType) {
        libraryItem.setType(itemType);
        itemType.getLibraryItems().add(libraryItem);
    }
}
