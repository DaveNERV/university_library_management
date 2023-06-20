package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.*;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.model.requests.OfferBookDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookInReaderInfoResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookUserInfoResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeBookOffertDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.UserBookOffertDTO;
import com.projectlicenta.librarymanagementsystem.repository.*;
import com.projectlicenta.librarymanagementsystem.services.BookOffertService;
import com.projectlicenta.librarymanagementsystem.services.ComService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.getAuthorString;
import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.getGenreString;

@Service
@RequiredArgsConstructor
public class BookOffertServiceImpl implements BookOffertService {

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    private final BookRepository bookRepository;

    private final OrderRepository orderRepository;

    private final BookOffertRepository bookOffertRepository;

    private final ReaderRepository readerRepository;

    private final String STATUS_FORMED = "Format";

    private final String STATUS_OFFERT = "Oferită";

    private final String STATUS_RETURNED = "Returnat";

    @Override
    public void offerBook(Integer orderId, List<Integer> bookIds) {
        User currentUser = userService.loadCurrentUser();
        // Find employee associated with this user
        Angajati currentEmployee = employeeRepository.findAngajatiByUser(currentUser)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee from context was not found"));

        Comenzi order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " was not found"));
        order.setEmployee(currentEmployee);

        // Получение списка книг заказа из базы данных
        List<CarteComanda> bookCommands = order.getCommandList();

        // Перебор книг заказа
        for (CarteComanda bookCommand : bookCommands) {
            Carti book = bookCommand.getBook();
            Integer bookId = book.getBookId();

            // Проверка, является ли книга отмеченной для сохранения
            if (!bookIds.contains(bookId)) {
                Integer nrExemplary = bookCommand.getNumber();

                // Создание объекта CartiOferite для сохранения в базу данных
                CartiOferite offeredBook = CartiOferite.builder()
                        .status(STATUS_OFFERT) // Установка начального статуса
                        .offerDate(LocalDateTime.now())
                        .deadline(LocalDateTime.now().plusMonths(1)) // Пример установки срока
                        .nrExemplary(nrExemplary) // Установка количества экземпляров из CarteComanda
                        .book(book)
                        .order(order)
                        .build();

                // Сохранение объекта CartiOferite в базу данных
                bookOffertRepository.save(offeredBook);
            } else {
                // Изменение статуса остальных книг на "anulat=true"
                bookCommand.setCanceled(true);
            }
        }

        order.setStatus(STATUS_FORMED);
        // Сохранение обновленных данных заказа в базу данных
        orderRepository.save(order);
    }

    @Override
    public List<EmployeeBookOffertDTO> findAllForEmployee(Specification<CartiOferite> spec){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return bookOffertRepository.findAll(spec).stream()
                .map(book -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime offerDate = book.getOfferDate();

                    long daysOffered = Duration.between(offerDate, now).toDays();

                    return EmployeeBookOffertDTO.builder()
                            .offerId(book.getOfferId())
                            .orderWithDate("Comanda cititorului " + book.getOrder().getOrderId() + " de la " + formatter.format(book.getOrder().getOrderDate()))
                            .bookName(book.getBook().getBookName())
                            .authorsList(getAuthorString(book.getBook()))
                            .publishYear(book.getBook().getPublishYear())
                            .publish(book.getBook().getPublish().getPublishName())
                            .offerDate(formatter.format(book.getOfferDate()))
                            .deadLine(formatter.format(book.getDeadline()))
                            .nrExemplary(book.getNrExemplary())
                            .status(book.getStatus())
                            .daysOffered(String.valueOf(daysOffered))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookInReaderInfoResponseDTO> findAllForUserInfo(String ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Find reader by ticket
        Cititori reader = readerRepository.findCititoriByTicketIgnoreCase(ticket)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with ticket " + ticket + " was not found"));

        // Get all orders for this reader
        List<Comenzi> orders = orderRepository.findAllByReader(reader);

        // Get all offered books from these orders
        List<CartiOferite> offeredBooks = orders.stream()
                .flatMap(order -> order.getOfferedBookList().stream())
                .filter(offeredBook -> STATUS_OFFERT.equals(offeredBook.getStatus())) // filter by status
                .collect(Collectors.toList());

        return offeredBooks.stream()
                .map(offeredBook -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime offerDate = offeredBook.getOfferDate();

                    long daysOffered = Duration.between(offerDate, now).toDays();

                    // Build BookUserInfoResponseDTO
                    BookUserInfoResponseDTO bookDTO = BookUserInfoResponseDTO.builder()
                            .bookId(offeredBook.getBook().getBookId())
                            .bookName(offeredBook.getBook().getBookName())
                            .ISBN(offeredBook.getBook().getISBN())
                            .publishPlace(offeredBook.getBook().getPublishPlace())
                            .publishYear(offeredBook.getBook().getPublishYear())
                            .genreList(getGenreString(offeredBook.getBook()))
                            .authorsList(getAuthorString(offeredBook.getBook()))
                            .publish(offeredBook.getBook().getPublish().getPublishName())
                            .UDK(offeredBook.getBook().getUDK())
                            .BBK(offeredBook.getBook().getBBK())
                            .build();

                    // Build the DTO object
                    return BookInReaderInfoResponseDTO.builder()
                            .bookDTO(bookDTO)
                            .nr(offeredBook.getNrExemplary())
                            .offerDate(formatter.format(offeredBook.getOfferDate()))
                            .deadLine(formatter.format(offeredBook.getDeadline()))
                            .daysOffered(String.valueOf(daysOffered) + " zile")
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserBookOffertDTO> findAllForUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        User user = userService.loadCurrentUser();

        // Find reader associated with this user
        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));

        // Get all orders for this reader
        List<Comenzi> orders = orderRepository.findAllByReader(reader);

        // Get all offered books from these orders
        List<CartiOferite> offeredBooks = orders.stream()
                .flatMap(order -> order.getOfferedBookList().stream())
                .collect(Collectors.toList());

        return offeredBooks.stream()
                .map(book -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime offerDate = book.getOfferDate();

                    long daysOffered = Duration.between(offerDate, now).toDays();

                    return UserBookOffertDTO.builder()
                            .bookId(book.getBook().getBookId())
                            .orderWithDate("Comanda " + book.getOrder().getOrderId() + " de la " + formatter.format(book.getOrder().getOrderDate()))
                            .bookName(book.getBook().getBookName())
                            .authorsList(getAuthorString(book.getBook()))
                            .publishYear(book.getBook().getPublishYear())
                            .publish(book.getBook().getPublish().getPublishName())
                            .offerDate(formatter.format(book.getOfferDate()))
                            .deadLine(formatter.format(book.getDeadline()))
                            .nrExemplary(book.getNrExemplary())
                            .status(book.getStatus())
                            .daysOffered(String.valueOf(daysOffered) + " zile")
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void returnBooks(List<Integer> ids){
        for (Integer id : ids) {
            Optional<CartiOferite> optionalBookOffer = bookOffertRepository.findById(id);

            if (optionalBookOffer.isPresent()) {
                CartiOferite bookOffer = optionalBookOffer.get();

                // Check if the book has already been returned
                if (!bookOffer.getStatus().equals(STATUS_RETURNED)) {
                    bookOffer.setStatus(STATUS_RETURNED);
                    bookOffertRepository.save(bookOffer);

                    // Increase the number of available books in the Carti entity
                    Carti book = bookOffer.getBook();
                    book.setNumber(book.getNumber() + bookOffer.getNrExemplary());
                    bookRepository.save(book);
                } else {
                    throw new BookAlreadyReturnedException("Book offer with id " + id + " has already been returned.");
                }
            } else {
                throw new BookOffertNotFoundException("Book offer with id " + id + " was not found.");
            }
        }
    }
}
