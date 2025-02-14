package com.example.finalfront.controller;

import com.example.finalfront.models.Genre;
import com.example.finalfront.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String getGenres(Model model) {
        model.addAttribute("genres", genreService.getGenres());
        return "genres/list";
    }

    @GetMapping("/{id}")
    public String getGenre(Model model, @PathVariable Long id) {
        model.addAttribute("genre", genreService.getGenre(id));
        return "genres/details";
    }

    @GetMapping("/create")
    public String createGenreForm(Model model) {
        return "genres/create";
    }

    @GetMapping("/{id}/update")
    public String updateGenreForm(Model model, @PathVariable Long id) {
        model.addAttribute("genre", genreService.getGenre(id));
        return "genres/update";
    }

    @PostMapping
    public String createGenre(Genre genre) {
        genreService.createGenre(genre);
        return "redirect:/genres";
    }

    @PostMapping("/{id}/update")
    public String updateGenre(@PathVariable Long id, Genre genre) {
        genreService.updateGenre(id, genre);
        return "redirect:/genres";
    }

    @PostMapping("/{id}/delete")
    public String deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}
