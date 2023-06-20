package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;

import java.util.List;

public interface GenreService {

    Genuri addGenre(Genuri genre);

    List<Genuri> getGenreList();

    Genuri updateGenre(Integer genreId, Genuri genre);

    void deleteGenre(Integer genreId);
}
