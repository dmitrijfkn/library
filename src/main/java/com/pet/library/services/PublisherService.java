package com.pet.library.services;

import com.pet.library.entities.Publisher;
import com.pet.library.repositories.PublsherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublsherRepository publsherRepository;

    @Autowired
    public PublisherService(PublsherRepository publsherRepository) {
        this.publsherRepository = publsherRepository;
    }

    public List<Publisher> findAll() {
        return publsherRepository.findAll();
    }

    public Publisher save(Publisher publisher) {
    return publsherRepository.save(publisher);
    }

    public Publisher findById(Long publisherId) {

        return publsherRepository.findById(publisherId).orElse(null);

    }
}
