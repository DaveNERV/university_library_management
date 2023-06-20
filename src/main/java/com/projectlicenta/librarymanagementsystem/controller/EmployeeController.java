package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.responses.*;
import com.projectlicenta.librarymanagementsystem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.toBookSpecification;
import static com.projectlicenta.librarymanagementsystem.services.mapper.BookOffertMapper.toBookOffertSpecification;
import static com.projectlicenta.librarymanagementsystem.services.mapper.EmployeeMapper.fromEmployeeToDashboard;
import static com.projectlicenta.librarymanagementsystem.services.mapper.OrderMapper.toOrderSpecification;
import static com.projectlicenta.librarymanagementsystem.services.mapper.ReaderMapper.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final OrderService orderService;

    private final ReaderService readerService;

    private final EmployeeService employeeService;

    private final BookOffertService bookOffertService;

    private final BookService bookService;

    @GetMapping
    public String employeeHomePage(@RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                   @RequestParam(value = "bookName", required = false) String bookName,
                                   @RequestParam(value = "ISBN", required = false) String ISBN,
                                   @RequestParam(value = "authorId", required = false) Integer authorId,
                                   @RequestParam(value = "publishYear", required = false) Short publishYear,
                                   @RequestParam(value = "bookId", required = false) Integer bookId,
                                   @RequestParam(value = "publishName", required = false) String publishName,
                                   @RequestParam(value = "BBK", required = false) String BBK,
                                   @RequestParam(value = "UDK", required = false) String UDK,
                                   @RequestParam(value = "genreId", required = false) Integer genreId,
                                   Model model) {

        List<BookEmployeeResponseDTO> booksEmployee = bookService.getbookList(toBookSpecification(bookName,
                ISBN, authorId, publishYear, bookId, publishName, genreId, onlyAvailable, BBK, UDK));

        model.addAttribute("booksEmployee", booksEmployee);

        model.addAttribute("onlyAvailable", onlyAvailable);
        model.addAttribute("bookName", bookName);
        model.addAttribute("ISBN", ISBN);
        model.addAttribute("authorId", authorId);
        model.addAttribute("publishYear", publishYear);
        model.addAttribute("bookId", bookId);
        model.addAttribute("publishName", publishName);
        model.addAttribute("genreId", genreId);
        model.addAttribute("BBK", BBK);
        model.addAttribute("UDK", UDK);

        return"employee/employee-home";
    }

    @GetMapping("/books/deletebook/{deleteBookId}")
    public String employeeDeleteBook(@PathVariable Integer deleteBookId) {
        bookService.deleteBook(deleteBookId);
        return "redirect:/employee";
    }

    @GetMapping("/books/newbook")
    public String employeeNewBook() {
        return"employee/employee-new-book";
    }

    @GetMapping(value="/books/changebookinfo")
    public String changeBookInfo(@RequestParam Integer bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "employee/employee-change-book-info";
    }

    @GetMapping(value="/reservations")
    public String reservations(Model model,
                               @RequestParam (required = false) String status,
                               @RequestParam (required = false) Integer readerId) {

        List<EmployeeOrderUserResponseDTO> orders = orderService.getAllOrders(toOrderSpecification(status, readerId));
        List<EmployeeUserSearchInOffertResponseDTO> readers = fromReaderListToReaderInOfferList(readerService.getReaderList());
        if (readerId != null) {
            Optional<EmployeeUserSearchInOffertResponseDTO> readerOptional = readers.stream()
                    .filter(reader -> readerId.equals(reader.getReaderId()))
                    .findFirst();

            if (readerOptional.isPresent()) {
                EmployeeUserSearchInOffertResponseDTO selectedReader = readerOptional.get();
                readers.remove(selectedReader);
                readers.add(0, selectedReader);
            }
        }
        model.addAttribute("status", status);
        model.addAttribute("orders", orders);
        model.addAttribute("readers", readers);
        model.addAttribute("readerId", readerId);
        return "employee/employee-reservations";
    }

    @GetMapping(value="/returnedbooks")
    public String returnedBooks(@RequestParam (required = false) Integer orderId,
                                @RequestParam (required = false) String ticket,
                                @RequestParam (required = false) Integer readerId,
                                @RequestParam (required = false) String offeredDateStr,
                                @RequestParam (required = false) String deadLineStr,
                                Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime offeredDate = (offeredDateStr != null && !offeredDateStr.isEmpty()) ? LocalDateTime.parse(offeredDateStr, formatter) : null;
        LocalDateTime deadLine = (deadLineStr != null && !deadLineStr.isEmpty()) ? LocalDateTime.parse(deadLineStr, formatter) : null;

        List<EmployeeBookOffertDTO> bookOffertDTOs = bookOffertService
                .findAllForEmployee(toBookOffertSpecification(ticket, orderId, readerId, offeredDate, deadLine));
        List<EmployeeUserSearchInOffertResponseDTO> readers = fromReaderListToReaderInOfferList(readerService.getReaderList());
        if (readerId != null) {
            Optional<EmployeeUserSearchInOffertResponseDTO> readerOptional = readers.stream()
                    .filter(reader -> readerId.equals(reader.getReaderId()))
                    .findFirst();

            if (readerOptional.isPresent()) {
                EmployeeUserSearchInOffertResponseDTO selectedReader = readerOptional.get();
                readers.remove(selectedReader);
                readers.add(0, selectedReader);
            }
        }

        boolean hasBooksOffered = bookOffertDTOs.stream().anyMatch(book -> "Oferită".equals(book.getStatus()));
        model.addAttribute("hasBooksOffered", hasBooksOffered);
        model.addAttribute("readers", readers);

        model.addAttribute("ticket", ticket);
        model.addAttribute("orderId", orderId);
        model.addAttribute("readerId", readerId);
        model.addAttribute("offeredDate", offeredDateStr);
        model.addAttribute("deadLine", deadLineStr);

        model.addAttribute("bookOfferts", bookOffertDTOs);
        return "employee/employee-returned-books";
    }

    @GetMapping(value="/users/showusers")
    public String showUsers(Model model,
                            @RequestParam(required = false) String ticket,
                            @RequestParam(required = false) String firstName,
                            @RequestParam(required = false) String lastName,
                            @RequestParam(required = false) String surName,
                            @RequestParam(required = false) String email) {

        List<Cititori> readers = readerService.getReaderList(toReaderSpecification(ticket, firstName, lastName, surName, email));

        model.addAttribute("readers", fromReaderList(readers));

        model.addAttribute("ticket", ticket);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("surName", surName);
        model.addAttribute("email", email);

        return "employee/employee-show-users";
    }

    @GetMapping(value="/users/showuserinfo")
    public String showUserInfo(@RequestParam String ticket,
                               Model model) {
        ReaderElementDto reader = fromReaderToElement(readerService.getReader(ticket));

        List<BookInReaderInfoResponseDTO> bookOffertDTOs = bookOffertService.findAllForUserInfo(ticket);
        List<OfferedBooksReaderInfoResponseDTO> reservedBooks = orderService.getReservedBooks(ticket);
        model.addAttribute("user", reader);
        model.addAttribute("bookList", bookOffertDTOs);
        model.addAttribute("reservedBooks", reservedBooks);

        return "employee/employee-show-user-info";
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public DashboardEmployeeDTO getReaderFromContext() {
        Angajati employee = employeeService.loadCurrentEmployee();
        DashboardEmployeeDTO dashboardEmployeeDTO = fromEmployeeToDashboard(employee);
        return dashboardEmployeeDTO;
    }
}


