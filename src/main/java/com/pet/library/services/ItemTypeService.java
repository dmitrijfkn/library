package com.pet.library.services;

import com.pet.library.entities.ItemType;
import com.pet.library.repositories.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {
    private final ItemTypeRepository itemTypeRepository;

    @Autowired
    public ItemTypeService(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    public ItemType findById(Long typeId) {
        return itemTypeRepository.findById(typeId).orElse(null);
    }

    public Object save(ItemType itemType) {
        return itemTypeRepository.save(itemType);
    }

    public List<ItemType> findAll() {
        return itemTypeRepository.findAll();
    }
}
