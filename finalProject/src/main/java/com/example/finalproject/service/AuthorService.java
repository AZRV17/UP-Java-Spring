package com.example.finalproject.service;

import com.example.finalproject.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();

    Author getAuthorById(Long id);

    Author saveAuthor(Author author);

    void deleteAuthor(Long id);

    Author updateAuthor(Author author);
}
