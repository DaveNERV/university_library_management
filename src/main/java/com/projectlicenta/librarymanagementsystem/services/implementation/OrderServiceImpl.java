package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.BookNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.EmployeeNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.OrderNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.ReaderNotFoundException;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.model.requests.OrderAndBooksDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.*;
import com.projectlicenta.librarymanagementsystem.repository.*;
import com.projectlicenta.librarymanagementsystem.services.ComService;
import com.projectlicenta.librarymanagementsystem.services.OrderService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ComRepository comRepository;

    private final ReaderRepository readerRepository;

    private final EmployeeRepository employeeRepository;

    private final BookRepository bookRepository;

    private final UserService userService;

    private final ComService comService;

    private final OrderRepository orderRepository;

    private final BookOrderRepository bookOrderRepository;

    private final String CREATED_ORDER = "Se prelucreaza";

    private final String FINISHED_ORDER = "acceptată";

    @Override
    public void createCommand(List<OrderAndBooksDTO> orderAndBooksDTOList) {
        // Get currently logged in user
        User user = userService.loadCurrentUser();

        // Find reader associated with this user
        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));

        Comenzi order = Comenzi.builder()
                .orderDate(LocalDateTime.now())  // Set the current date and time
                .reader(reader)
                .status(CREATED_ORDER)
                .build();

        // Save order
        Comenzi savedOrder = orderRepository.save(order);

        // Loop over each bookOrderDto in the list
        for (OrderAndBooksDTO orderAndBooksDTO : orderAndBooksDTOList) {
            Carti book = bookRepository.findById(orderAndBooksDTO.getBookId())
                    .orElseThrow(() -> new BookNotFoundException("Book with id " + orderAndBooksDTO.getBookId() + " was not found"));

            Integer price = book.getPrice() * orderAndBooksDTO.getNumber();

            CarteComanda bookOrder = CarteComanda.builder()
                    .number(orderAndBooksDTO.getNumber())
                    .price(price)
                    .book(book)
                    .order(savedOrder)
                    .build();

            bookOrderRepository.save(bookOrder);

            comService.deleteComByReader(reader);

            book.setNumber(book.getNumber() - orderAndBooksDTO.getNumber());
            bookRepository.save(book); // Сохранение обновленных данных книги
        }
    }

    public List<OrderUserResponseDTO> getCommandForUserFromContext(){
        // Get currently logged in user
        User user = userService.loadCurrentUser();

        // Find reader associated with this user
        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));

        // Get orders for this user
        List<Comenzi> userOrders = orderRepository.findAllByReader(reader);

        // Convert each order to OrderUserResponseDTO
        List<OrderUserResponseDTO> orderUserResponseDTOs = userOrders.stream()
                .map(order -> {
                    Angajati responsible = order.getEmployee();
                    String responsibleString = "";

                    if(responsible != null && responsible.getUser() != null){
                        responsibleString = responsible.getUser().getFirstName() + " "
                                + responsible.getUser().getLastName() + ", "
                                + responsible.getJobTitle();
                    }

                    LocalDateTime orderDate = order.getOrderDate().truncatedTo(ChronoUnit.MINUTES);
                    String formattedOrderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    // Find all CarteComanda for this order
                    List<CarteComanda> booksInOrder = bookOrderRepository.findAllByOrder(order);

                    // Calculate total price for this order
                    Integer totalPrice = booksInOrder.stream()
                            .mapToInt(CarteComanda::getPrice)
                            .sum();

                    OrderUserResponseDTO orderUserResponseDTO = OrderUserResponseDTO.builder()
                            .orderId(order.getOrderId())
                            .status(order.getStatus())
                            .orderDate(formattedOrderDate)
                            .responsible(responsibleString)
                            .price(String.valueOf(totalPrice))
                            .build();

                    return orderUserResponseDTO;
                })
                .collect(Collectors.toList());

        return orderUserResponseDTOs;
    }

    @Override
    public List<EmployeeOrderUserResponseDTO> getAllOrders(Specification<Comenzi> spec) {
        // Get all orders
        List<Comenzi> allOrders = orderRepository.findAll(spec);

        // Convert each order to EmployeeOrderUserResponseDTO
        List<EmployeeOrderUserResponseDTO> orderResponseDTOs = allOrders.stream()
                .map(order -> {
                    Angajati responsible = order.getEmployee();
                    String responsibleString = "";

                    if(responsible != null && responsible.getUser() != null){
                        responsibleString = responsible.getJobTitle() + " "
                                + responsible.getUser().getFirstName() + " "
                                + responsible.getUser().getLastName() + " "
                                + responsible.getUser().getSurName();
                    }

                    LocalDateTime orderDate = order.getOrderDate().truncatedTo(ChronoUnit.MINUTES);
                    String formattedOrderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    // Find all CarteComanda for this order
                    List<CarteComanda> booksInOrder = bookOrderRepository.findAllByOrder(order);

                    // Calculate total price for this order
                    Integer totalPrice = booksInOrder.stream()
                            .mapToInt(CarteComanda::getPrice)
                            .sum();

                    Cititori reader = order.getReader();
                    String readerString = "";

                    if(reader != null && reader.getUser() != null){
                        readerString = reader.getUser().getFirstName() + " "
                                + reader.getUser().getLastName() + " "
                                + reader.getUser().getSurName();
                    }

                    EmployeeOrderUserResponseDTO orderResponseDTO = EmployeeOrderUserResponseDTO.builder()
                            .orderId(order.getOrderId())
                            .status(order.getStatus())
                            .orderDate(formattedOrderDate)
                            .responsible(responsibleString)
                            .price(String.valueOf(totalPrice))
                            .reader(readerString)
                            .build();

                    return orderResponseDTO;
                })
                .collect(Collectors.toList());

        return orderResponseDTOs;
    }

    @Override
    public List<BookInOrderResponseDTO> getBooksInOrderByOrderId(Integer orderId){
        // Find the order
        Comenzi order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " was not found"));

        // Get all the books in the order
        List<CarteComanda> booksInOrder = bookOrderRepository.findAllByOrder(order);

        // Convert each book in the order to a BookInOrderResponseDTO
        List<BookInOrderResponseDTO> bookInOrderResponseDTOs = booksInOrder.stream()
                .map(bookInOrder -> {
                    // Get the book
                    Carti book = bookInOrder.getBook();

                    // Create BookUserResponseDTO using the provided method
                    BookUserResponseDTO bookUserResponseDTO = fromBookToBookDto(book);

                    // Create BookInOrderResponseDTO
                    BookInOrderResponseDTO bookInOrderResponseDTO = BookInOrderResponseDTO.builder()
                            .bookDTO(bookUserResponseDTO)
                            .nr(bookInOrder.getNumber())
                            .price(bookInOrder.getPrice())
                            .canceled(bookInOrder.getCanceled())
                            .build();

                    return bookInOrderResponseDTO;
                })
                .collect(Collectors.toList());

        return bookInOrderResponseDTOs;
    }

    @Override
    public List<OfferedBooksReaderInfoResponseDTO> getReservedBooks(String ticket) {
        // Date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Find reader by ticket
        Cititori reader = readerRepository.findCititoriByTicketIgnoreCase(ticket)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with ticket " + ticket + " was not found"));

        // Get all orders for this reader with status "Se prelucrează"
        List<Comenzi> orders = orderRepository.findAllByReaderAndStatus(reader, CREATED_ORDER);

        // Collect all books from all orders
        List<CarteComanda> bookOrders = new ArrayList<>();
        for (Comenzi order : orders) {
            bookOrders.addAll(bookOrderRepository.findAllByOrder(order));
        }

        // Convert each bookOrder to OfferedBooksReaderInfoResponseDTO
        List<OfferedBooksReaderInfoResponseDTO> reservedBooks = bookOrders.stream()
                .map(bookOrder -> {
                    // Get the book
                    Carti book = bookOrder.getBook();

                    // Create BookUserInfoResponseDTO
                    BookUserInfoResponseDTO bookUserInfoResponseDTO = BookUserInfoResponseDTO.builder()
                            .bookId(book.getBookId())
                            .bookName(book.getBookName())
                            .ISBN(book.getISBN())
                            .publishPlace(book.getPublishPlace())
                            .publishYear(book.getPublishYear())
                            .genreList(getGenreString(book)) // You will need to implement getGenreList() method
                            .authorsList(getAuthorString(book)) // Assuming getAuthorString() is a method that generates a list of authors
                            .publish(book.getPublish().getPublishName())
                            .UDK(book.getUDK())
                            .BBK(book.getBBK())
                            .build();

                    // Format the order date
                    Comenzi order = bookOrder.getOrder();
                    String formattedOrderDate = formatter.format(order.getOrderDate());

                    // Create OfferedBooksReaderInfoResponseDTO
                    OfferedBooksReaderInfoResponseDTO offeredBooksReaderInfoResponseDTO = OfferedBooksReaderInfoResponseDTO.builder()
                            .bookDTO(bookUserInfoResponseDTO)
                            .nr(bookOrder.getNumber())
                            .orderWithDate("Comanda cititorului " + order.getOrderId() + " de la " + formattedOrderDate)
                            .build();

                    return offeredBooksReaderInfoResponseDTO;
                })
                .collect(Collectors.toList());

        return reservedBooks;
    }


    @Override
    public EmployeeOrderUserResponseDTO cancelOrder(Integer orderId) {
        // Get currently logged in user
        User currentUser = userService.loadCurrentUser();
        // Find employee associated with this user
        Angajati currentEmployee = employeeRepository.findAngajatiByUser(currentUser)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee from context was not found"));

        // Fetch the order
        Comenzi order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " was not found"));

        List<CarteComanda> bookCommands = order.getCommandList();
        for (CarteComanda bookCommand : bookCommands) {
            Carti book = bookCommand.getBook();
            Integer quantity = bookCommand.getNumber();
            book.setNumber(book.getNumber() + quantity); // Increase the quantity of books in stock
        }

        // Cancel the order and set the current employee as the one responsible for the cancellation
        order.setStatus("Anulat");
        order.setEmployee(currentEmployee);

        // Save the order
        orderRepository.save(order);

        LocalDateTime orderDate = order.getOrderDate().truncatedTo(ChronoUnit.MINUTES);
        String formattedOrderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Prepare the response
        EmployeeOrderUserResponseDTO response = new EmployeeOrderUserResponseDTO();
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setOrderDate(formattedOrderDate);
        response.setPrice(order.getCommandList().stream().mapToInt(CarteComanda::getPrice).sum() + ""); // sum all prices
        response.setResponsible(currentEmployee.getJobTitle() + " "
                + currentEmployee.getUser().getFirstName() + " "
                + currentEmployee.getUser().getLastName() + " "
                + currentEmployee.getUser().getSurName());
        response.setReader(order.getReader().getUser().getFirstName() + " "
                + order.getReader().getUser().getLastName() + " "
                + order.getReader().getUser().getSurName());

        return response;
    }
}
