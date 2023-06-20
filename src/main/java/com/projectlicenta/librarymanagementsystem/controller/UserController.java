package com.projectlicenta.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlicenta.librarymanagementsystem.model.responses.*;
import com.projectlicenta.librarymanagementsystem.services.BookOffertService;
import com.projectlicenta.librarymanagementsystem.services.BookService;
import com.projectlicenta.librarymanagementsystem.services.OrderService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.toBookSpecification;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final OrderService orderService;

    private final UserService userService;

    private final BookService bookService;

    private final BookOffertService bookOffertService;

    @GetMapping
    public String browseBooks(@RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                              @RequestParam(value = "bookName", required = false) String bookName,
                              @RequestParam(value = "ISBN", required = false) String ISBN,
                              @RequestParam(value = "authorId", required = false) Integer authorId,
                              @RequestParam(value = "publishYear", required = false) Short publishYear,
                              @RequestParam(value = "bookId", required = false) Integer bookId,
                              @RequestParam(value = "publishName", required = false) String publishName,
                              @RequestParam(value = "genreId", required = false) Integer genreId,
                              Model model) {

        List<BookEmployeeResponseDTO> booksUser = bookService.getbookList(toBookSpecification(bookName, ISBN, authorId,
                publishYear, bookId, publishName, genreId, onlyAvailable, null, null));

        model.addAttribute("books", booksUser);

        model.addAttribute("onlyAvailable", onlyAvailable);
        model.addAttribute("bookName", bookName);
        model.addAttribute("ISBN", ISBN);
        model.addAttribute("authorId", authorId);
        model.addAttribute("publishYear", publishYear);
        model.addAttribute("bookId", bookId);
        model.addAttribute("publishName", publishName);
        model.addAttribute("genreId", genreId);

        return "user/user-home";
    }

    @PostMapping(value = "/browsebooks/payreservation")
    public String payReservation() {
        return "user/user-pay-reservation";
    }

    @GetMapping(value="/browsebooks/savereservation")
    public String saveBookReservations(Model model) {
        return "redirect:/user/yourreservations";
    }

    @GetMapping(value="/yourreservations")
    public String reservations(Model model) {
        List<OrderUserResponseDTO> orders = orderService.getCommandForUserFromContext();
        model.addAttribute("orders", orders);
        return "user/user-your-reservations";
    }

    @GetMapping(value="/FAQ")
    public String FAQ() {
        return "user/user-FAQ";
    }

    @GetMapping(value="/yourbooks")
    public String yourBooks(Model model) {

        List<UserBookOffertDTO> offeredBooks = bookOffertService.findAllForUser();


        model.addAttribute("offeredBooks", offeredBooks);

        return "user/user-your-books";
    }
}
