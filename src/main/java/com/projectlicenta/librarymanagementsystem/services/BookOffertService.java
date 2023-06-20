package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.CartiOferite;
import com.projectlicenta.librarymanagementsystem.model.requests.OfferBookDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookInReaderInfoResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeBookOffertDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.UserBookOffertDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookOffertService {

    void offerBook(Integer orderId, List<Integer> bookIds);

    List<EmployeeBookOffertDTO> findAllForEmployee(Specification<CartiOferite> spec);

    void returnBooks(List<Integer> ids);

    List<UserBookOffertDTO> findAllForUser();

    List<BookInReaderInfoResponseDTO> findAllForUserInfo(String ticket);
}
