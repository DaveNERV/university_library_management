package com.projectlicenta.librarymanagementsystem.repository.specifications;

import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Angajati> hasJobTitle(String jobTitle) {
        return (jobTitle == null || jobTitle.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("jobTitle"), jobTitle);
    }

    public static Specification<Angajati> hasFirstName(String firstName) {
        return (firstName == null || firstName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("firstName"), firstName);
    }

    public static Specification<Angajati> hasLastName(String lastName) {
        return (lastName == null || lastName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("lastName"), lastName);
    }

    public static Specification<Angajati> hasSurName(String surName) {
        return (surName == null || surName.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("surName"), surName);
    }

    public static Specification<Angajati> hasEmail(String email) {
        return (email == null || email.isEmpty())
                ? (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("email"), email);
    }
}
