package com.projectlicenta.librarymanagementsystem.repository.specifications;

import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.Editura;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.SetJoin;

public class BookSpecification {

    public static Specification<Carti> hasBookName(String bookName) {
        return (root, query, criteriaBuilder) -> bookName == null || bookName.isEmpty() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("bookName")), "%" + bookName.toLowerCase() + "%");
    }

    public static Specification<Carti> hasISBN(String ISBN) {
        return (root, query, criteriaBuilder) -> ISBN == null || ISBN.isEmpty() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("ISBN")), "%" + ISBN.toLowerCase() + "%");
    }

    public static Specification<Carti> hasAuthor(Integer authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return null;
            }

            ListJoin<Carti, Autori> joinAuthors = root.joinList("authorsList");
            return criteriaBuilder.equal(joinAuthors.get("authorId"), authorId);
        };
    }

    public static Specification<Carti> hasPublishYear(Short publishYear) {
        return (root, query, criteriaBuilder) -> publishYear == null ? null :
                criteriaBuilder.equal(root.get("publishYear"), publishYear);
    }

    public static Specification<Carti> hasBookId(Integer bookId) {
        return (root, query, criteriaBuilder) -> bookId == null ? null :
                criteriaBuilder.equal(root.get("bookId"), bookId);
    }

    public static Specification<Carti> hasPublishName(String publishName) {
        return (root, query, criteriaBuilder) -> {
            if (publishName == null || publishName.isEmpty()) {
                return null;
            }

            Join<Carti, Editura> joinPublish = root.join("publish");
            return criteriaBuilder.equal(joinPublish.get("publishName"), publishName);
        };
    }

    public static Specification<Carti> hasGenre(Integer genreId) {
        return (root, query, criteriaBuilder) -> {
            if (genreId == null) {
                return null;
            }

            ListJoin<Carti, Genuri> joinGenres = root.joinList("genreList");
            return criteriaBuilder.equal(joinGenres.get("genreId"), genreId);
        };
    }

    public static Specification<Carti> isAvailable() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("number"), 0);
    }

    public static Specification<Carti> hasUDK(String UDK) {
        return (root, query, criteriaBuilder) -> UDK == null || UDK.isEmpty() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("UDK")), "%" + UDK.toLowerCase() + "%");
    }

    public static Specification<Carti> hasBBK(String BBK) {
        return (root, query, criteriaBuilder) -> BBK == null || BBK.isEmpty() ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("BBK")), "%" + BBK.toLowerCase() + "%");
    }
}
