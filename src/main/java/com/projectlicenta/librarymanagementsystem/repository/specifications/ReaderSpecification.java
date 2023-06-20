package com.projectlicenta.librarymanagementsystem.repository.specifications;

import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import org.springframework.data.jpa.domain.Specification;

public class ReaderSpecification {

    public static Specification<Cititori> hasTicket(String ticket) {
        return (ticket == null || ticket.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ticket"), ticket);
    }

    public static Specification<Cititori> hasFirstName(String firstName) {
        return (firstName == null || firstName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("firstName"), firstName);
    }

    public static Specification<Cititori> hasLastName(String lastName) {
        return (lastName == null || lastName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("lastName"), lastName);
    }

    public static Specification<Cititori> hasSurName(String surName) {
        return (surName == null || surName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("surName"), surName);
    }

    public static Specification<Cititori> hasEmail(String email) {
        return (email == null || email.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("email"), email);
    }
}
