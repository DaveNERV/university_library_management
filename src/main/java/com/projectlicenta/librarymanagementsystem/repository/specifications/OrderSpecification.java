package com.projectlicenta.librarymanagementsystem.repository.specifications;

import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Comenzi> withReaderId(Integer readerId) {
        if (readerId == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("reader").get("readerId"), readerId);
        }
    }

    public static Specification<Comenzi> withStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("status"), status);
        }
    }
}
