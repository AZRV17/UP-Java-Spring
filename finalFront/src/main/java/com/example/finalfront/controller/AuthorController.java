package com.example.finalfront.controller;

import com.example.finalfront.models.Author;
import com.example.finalfront.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllAuthors(Model model) {
        List<Author> authors = authorService.getAuthors();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "authors/details";
    }

    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/create";
    }

    @GetMapping("/{id}/update")
    public String updateAuthorForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "authors/update";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute Author author) {
        authorService.createAuthor(author);
        return "redirect:/";
    }

    @PostMapping("/{id}/update")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        authorService.updateAuthor(id, author);
        return "redirect:/authors/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
