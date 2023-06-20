package com.projectlicenta.librarymanagementsystem.repository.specifications;

import com.projectlicenta.librarymanagementsystem.model.entities.CartiOferite;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class OfferedBooksSpecification {
    public static Specification<CartiOferite> hasOrderNumber(Integer orderId) {
        return (root, query, criteriaBuilder) -> {
            if(orderId == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("order").get("orderId"), orderId);
        };
    }

    public static Specification<CartiOferite> hasReaderTicket(String ticket) {
        return (root, query, criteriaBuilder) -> {
            if(ticket == null || ticket.trim().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            System.out.println(2);
            return criteriaBuilder.equal(root.get("order").get("reader").get("ticket"), ticket);
        };
    }

    public static Specification<CartiOferite> isOfferedAfter(LocalDateTime afterDate) {
        return (root, query, criteriaBuilder) -> {
            if(afterDate == null){
                return criteriaBuilder.conjunction();
            }
            System.out.println(3);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("offerDate"), afterDate);
        };
    }

    public static Specification<CartiOferite> isOfferedBefore(LocalDateTime beforeDate) {
        return (root, query, criteriaBuilder) -> {
            if(beforeDate == null){
                return criteriaBuilder.conjunction();
            }
            System.out.println(4);
            return criteriaBuilder.lessThanOrEqualTo(root.get("offerDate"), beforeDate);
        };
    }

    public static Specification<CartiOferite> hasReaderId(Integer readerId) {
        return (root, query, criteriaBuilder) -> {
            if(readerId == null){
                return criteriaBuilder.conjunction();
            }

            Join<CartiOferite, Comenzi> joinOrder = root.join("order");
            Join<Comenzi, Cititori> joinReader = joinOrder.join("reader");

            return criteriaBuilder.equal(joinReader.get("readerId"), readerId);
        };
    }
}
