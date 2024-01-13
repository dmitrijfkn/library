package com.pet.library.services;

import com.pet.library.entities.Author;
import com.pet.library.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Set<Author> findAllById(Iterable<Long> authorIds) {
        return new HashSet<>(authorRepository.findAllById(authorIds));
    }
}
