package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import com.projectlicenta.librarymanagementsystem.model.requests.OrderAndBooksDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderService {

    void createCommand(List<OrderAndBooksDTO> orderAndBooksDTO);

    List<OrderUserResponseDTO> getCommandForUserFromContext();

    List<BookInOrderResponseDTO> getBooksInOrderByOrderId(Integer orderId);

    List<EmployeeOrderUserResponseDTO> getAllOrders(Specification<Comenzi> spec);

    EmployeeOrderUserResponseDTO cancelOrder(Integer orderId);

    List<OfferedBooksReaderInfoResponseDTO> getReservedBooks(String ticket);
}
