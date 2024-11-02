package com.example.finalproject.service;

import com.example.finalproject.models.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book saveBook(Book book);

    void deleteBookById(Long id);

    Book updateBook(Book book);
}