package com.example.finalfront.controller;

import com.example.finalfront.dto.BookDTO;
import com.example.finalfront.models.Author;
import com.example.finalfront.models.Book;
import com.example.finalfront.models.Genre;
import com.example.finalfront.models.Publisher;
import com.example.finalfront.service.AuthorService;
import com.example.finalfront.service.BookService;
import com.example.finalfront.service.GenreService;
import com.example.finalfront.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final PublisherService publisherService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "books/details";
    }

    @GetMapping("/create")
    public String createBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("publishers", publisherService.getPublishers());
        return "books/create";
    }

    @GetMapping("/{id}/update")
    public String updateBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("publishers", publisherService.getPublishers());
        model.addAttribute("book", book);
        return "books/update";
    }

    @PostMapping
    public String createBook(@ModelAttribute BookDTO bookDTO) {

        bookService.createBook(bookDTO);
        return "redirect:/books";
    }


    @PostMapping("/{id}/update")
    public String updateBook(@PathVariable Long id, Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
