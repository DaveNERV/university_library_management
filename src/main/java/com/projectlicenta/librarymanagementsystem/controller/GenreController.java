package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.GenreDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.projectlicenta.librarymanagementsystem.services.mapper.GenreMapper.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponseDTO addGenre(@RequestBody @Valid GenreDTO genreDTO) {
        return fromGenre(genreService.addGenre(toGenre(genreDTO)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GenreResponseDTO> getAllGenres() {
        return fromGenreList(genreService.getGenreList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GenreResponseDTO updateGenre(@PathVariable Integer id,
                             @Valid @RequestBody GenreDTO genreDTO) {
        return fromGenre(genreService.updateGenre(id, toGenre(genreDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
    }
}
