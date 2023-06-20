package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.BookDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookUpdateResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody @Valid BookDTO bookDTO) {
            bookService.addBook(toBook(bookDTO), bookDTO.getPublishName(),
                    bookDTO.getGenreList(), bookDTO.getAuthorsList());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookUpdateResponseDTO getBookById(@PathVariable(name = "id") Integer id){
        return fromBook(bookService.getBook(id));
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable Integer bookId,
                             @Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBook(bookId, toBook(bookDTO), bookDTO.getPublishName(),
                bookDTO.getGenreList(), bookDTO.getAuthorsList());
    }
}
