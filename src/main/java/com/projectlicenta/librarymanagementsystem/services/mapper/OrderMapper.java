package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.CartiOferite;
import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import com.projectlicenta.librarymanagementsystem.repository.specifications.OfferedBooksSpecification;
import com.projectlicenta.librarymanagementsystem.repository.specifications.OrderSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderMapper {
    public static Specification<Comenzi> toOrderSpecification(String status, Integer readerId) {
        return Specification
                .where(OrderSpecification.withReaderId(readerId))
                .and(OrderSpecification.withStatus(status));
    }
}
