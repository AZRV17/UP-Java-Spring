package com.example.finalproject.controllers;

import com.example.finalproject.dto.BookDTO;
import com.example.finalproject.models.Book;
import com.example.finalproject.service.AuthorService;
import com.example.finalproject.service.BookService;
import com.example.finalproject.service.GenreService;
import com.example.finalproject.service.PublisherService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
public class BookApiController {
    @Qualifier("bookService")
    @Autowired
    private BookService bookService;

    @Qualifier("publisherService")
    @Autowired
    private PublisherService publisherService;

    @Qualifier("authorService")
    @Autowired
    private AuthorService authorService;

    @Qualifier("genreService")
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        log.info("Found {} books123", books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookDTO book) {

        log.info("Received book: {}", book.getGenreId());
        Book newBook = new Book();

        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setGenre(genreService.getGenreById(book.getGenreId()));
        newBook.setPublisher(publisherService.getPublisherById(book.getPublisherId()));
        newBook.setAuthors(book.getAuthorIds().stream().map(authorService::getAuthorById).toList());

        Book savedBook = bookService.saveBook(newBook);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
}
