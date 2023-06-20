package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeOrderUserResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.BookOffertService;
import com.projectlicenta.librarymanagementsystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-offer")
public class BookOfferController {

    private final OrderService orderService;

    private final BookOffertService bookOffertService;

    @PutMapping("/{offerIds}")
    @ResponseStatus(HttpStatus.OK)
    public void returnBook(@PathVariable List<Integer> offerIds) {
        bookOffertService.returnBooks(offerIds);
    }
}
