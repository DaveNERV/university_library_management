package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.GenreNotFoundException;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.repository.GenreRepository;
import com.projectlicenta.librarymanagementsystem.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Genuri addGenre(Genuri genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genuri> getGenreList() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genuri updateGenre(Integer genreId, Genuri genre){
        Genuri findedGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre  with id " + genreId  + " was not found"));

        findedGenre.setGenreName(genre.getGenreName());
        return genreRepository.save(findedGenre);
    }

    @Transactional
    @Override
    public void deleteGenre(Integer genreId) {
        Genuri findedGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + genreId  + " was not found"));
        genreRepository.deleteById(findedGenre.getGenreId());
    }
}
