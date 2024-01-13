package com.pet.library.services;

import com.pet.library.entities.Category;
import com.pet.library.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Object save(Category category) {
        return categoryRepository.save(category);
    }

    public Set<Category> findAllById(Set<Long> categoryIds) {
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}
