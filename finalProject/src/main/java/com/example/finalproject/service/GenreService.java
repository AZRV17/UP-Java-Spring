package com.example.finalproject.service;

import com.example.finalproject.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getGenres();

    Genre getGenreById(Long id);

    Genre saveGenre(Genre genre);

    void deleteGenre(Long id);

    Genre updateGenre(Genre genre);
}
