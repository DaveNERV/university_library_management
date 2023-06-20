package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.responses.BookEmployeeResponseDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookEmployeeResponseDTO> getbookList(Specification<Carti> spec);

    void addBook(Carti book, String publishName, List<Integer> genreList, List<Integer> authorsList);

    void updateBook(Integer bookId, Carti book, String publishName,
                             List<Integer> genreList, List<Integer> authorsList);

    Carti getBook(Integer bookId);

    void deleteBook(Integer bookId);
}
