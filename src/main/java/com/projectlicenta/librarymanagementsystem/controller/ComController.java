package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.GenreDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.ComResponseList;
import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.ComService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com")
public class ComController {

    private final ComService comService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComResponseList addBookToCart(Integer bookId) {
        return comService.addCom(bookId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ComResponseList getCom() {
        return comService.getCom();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ComResponseList deleteCom(Integer bookId) {
        return comService.deleteCom(bookId);
    }
}
