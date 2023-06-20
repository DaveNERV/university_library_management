package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.BookNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.ReaderNotFoundException;
import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Com;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.responses.ComResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.ComResponseList;
import com.projectlicenta.librarymanagementsystem.repository.BookRepository;
import com.projectlicenta.librarymanagementsystem.repository.ComRepository;
import com.projectlicenta.librarymanagementsystem.repository.ReaderRepository;
import com.projectlicenta.librarymanagementsystem.services.ComService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComServiceImpl implements ComService {

    private final ComRepository comRepository;

    private final ReaderRepository readerRepository;

    private final BookRepository bookRepository;

    private final UserService userService;

    @Override
    public ComResponseList addCom(Integer bookId) {
        User user = userService.loadCurrentUser();

        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));;
        Carti book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId  + " was not found"));

        // Check if there are enough books in stock
//        if (book.getNumber() <= 0) {
//            throw new RuntimeException("The book is out of stock");
//        }

        Com com = comRepository.findComByReaderAndAndBook(reader, book);

        if (com != null) {
            com.setNr(com.getNr() + 1);
        } else {
            com = new Com();
            com.setBook(book);
            com.setReader(reader);
            com.setNr(1);
        }

        // Decrease the number of books in stock
        book.setNumber(book.getNumber() - 1);
        bookRepository.save(book);

        comRepository.save(com);

        return  createComResponse(reader);
    }

    @Override
    public ComResponseList deleteCom(Integer bookId) {
        User user = userService.loadCurrentUser();

        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));
        Carti book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId  + " was not found"));

        Com comItem = comRepository.findComByReaderAndAndBook(reader, book);

        if (comItem != null) {
            if (comItem.getNr() > 1) {
                comItem.setNr(comItem.getNr() - 1);
                comRepository.save(comItem);
            } else {
                comRepository.delete(comItem);
            }


            book.setNumber(book.getNumber() + 1);
            bookRepository.save(book);
        }

        return createComResponse(reader);
    }

    @Override
    public ComResponseList getCom() {
        User user = userService.loadCurrentUser();
        Cititori reader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader from context was not found"));

        return createComResponse(reader);
    }

    private ComResponseList createComResponse(Cititori reader){
        List<Com> comList = comRepository.findAllByReader(reader);

        Integer totalPrice = comList.stream()
                .mapToInt(com -> com.getBook().getPrice() * com.getNr())
                .sum();

        List<ComResponseDTO> comResponseList = comList.stream()
                .map(com -> ComResponseDTO.builder()
                        .bookDTO(BookMapper.fromBookToBookDto(com.getBook()))
                        .nr(com.getNr())
                        .build())
                .collect(Collectors.toList());

        return ComResponseList.builder()
                .comList(comResponseList)
                .totalPrice(totalPrice)
                .build();
    }

    @Override
    public void deleteComByReader(Cititori reader){
        List<Com> comList = comRepository.findAllByReader(reader);

        for (Com com : comList) {
            comRepository.delete(com);

            // Увеличение количества книг после удаления каждой корзины
            Carti book = com.getBook();
            book.setNumber(book.getNumber() + com.getNr());
            bookRepository.save(book);
        }
    }
}
