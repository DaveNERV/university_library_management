package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.AuthorDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AuthorResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AuthorMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDTO addAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return fromAuthor(authorService.addAuthor(toAuthor(authorDTO)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDTO> getAllAuthor() {
        return fromAuthorList(authorService.getAuthorList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@PathVariable Integer id,
                             @Valid @RequestBody AuthorDTO authorDTO) {
        authorService.updateAuthor(id, toAuthor(authorDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
    }
}
