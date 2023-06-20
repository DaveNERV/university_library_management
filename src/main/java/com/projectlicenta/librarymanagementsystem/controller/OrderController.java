package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.OfferBookDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.OrderAndBooksDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookInOrderResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookUpdateResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeBookInOrderResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeOrderUserResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.BookOffertService;
import com.projectlicenta.librarymanagementsystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final BookOffertService bookOffertService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCommand(@RequestBody @Valid List<OrderAndBooksDTO> orderAndBooksDTO) {
        orderService.createCommand(orderAndBooksDTO);
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookInOrderResponseDTO> getBookInOrdersById(@RequestParam(value = "orderId") Integer orderId){
        return orderService.getBooksInOrderByOrderId(orderId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeOrderUserResponseDTO cancelOrder(@PathVariable Integer id) {
        return orderService.cancelOrder(id);
    }

    @PostMapping("/offer")
    @ResponseStatus(HttpStatus.CREATED)
    public void offerBook(@RequestBody @Valid OfferBookDTO offerBookDTO) {
        bookOffertService.offerBook(offerBookDTO.getOrder(), offerBookDTO.getBookIds());
    }
}
