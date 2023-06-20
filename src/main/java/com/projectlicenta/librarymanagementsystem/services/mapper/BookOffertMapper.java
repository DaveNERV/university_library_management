package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.CartiOferite;
import com.projectlicenta.librarymanagementsystem.repository.specifications.BookSpecification;
import com.projectlicenta.librarymanagementsystem.repository.specifications.OfferedBooksSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookOffertMapper {

    public static Specification<CartiOferite> toBookOffertSpecification(String ticket, Integer orderId, Integer readerId, LocalDateTime offerDate, LocalDateTime deadline) {
        return Specification
                .where(OfferedBooksSpecification.hasOrderNumber(orderId))
                .and(OfferedBooksSpecification.hasReaderTicket(ticket))
                .and(OfferedBooksSpecification.isOfferedAfter(offerDate))
                .and(OfferedBooksSpecification.isOfferedBefore(deadline))
                .and(OfferedBooksSpecification.hasReaderId(readerId));
    }
}
