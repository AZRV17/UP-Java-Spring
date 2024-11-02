package com.example.finalproject.service;

import com.example.finalproject.models.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getPublishers();

    Publisher getPublisherById(Long id);

    Publisher savePublisher(Publisher publisher);

    void deletePublisher(Long id);

    Publisher updatePublisher(Publisher publisher);
}
