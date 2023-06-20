package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.model.requests.BookDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookEmployeeResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookUpdateResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookUserResponseDTO;
import com.projectlicenta.librarymanagementsystem.repository.specifications.BookSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.stream.Collectors;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AuthorMapper.fromAuthorList;
import static com.projectlicenta.librarymanagementsystem.services.mapper.GenreMapper.fromGenre;
import static com.projectlicenta.librarymanagementsystem.services.mapper.GenreMapper.fromGenreList;

public class BookMapper {

    public static Specification<Carti> toBookSpecification(String bookName,String ISBN, Integer authorId,
                                                           Short publishYear, Integer bookId,
                                                           String publishName, Integer genreId, Boolean onlyAvailable,
                                                           String BBK, String UDK) {
        Specification<Carti> spec = Specification
                .where(BookSpecification.hasBookName(bookName))
                .and(BookSpecification.hasISBN(ISBN))
                .and(BookSpecification.hasAuthor(authorId))
                .and(BookSpecification.hasPublishYear(publishYear))
                .and(BookSpecification.hasBookId(bookId))
                .and(BookSpecification.hasPublishName(publishName))
                .and(BookSpecification.hasGenre(genreId))
                .and(BookSpecification.hasBBK(BBK))
                .and(BookSpecification.hasUDK(UDK));

        if (Boolean.TRUE.equals(onlyAvailable)) {
            spec = spec.and(BookSpecification.isAvailable());
        }

        return spec;
    }

    public static Carti toBook(BookDTO bookDTO) {

        return Carti.builder()
                .bookName(bookDTO.getBookName())
                .ISBN(bookDTO.getISBN())
                .publishPlace(bookDTO.getPublishPlace())
                .publishYear(bookDTO.getPublishYear())
                .photo(bookDTO.getPhoto())
                .BBK(bookDTO.getBBK())
                .UDK(bookDTO.getUDK())
                .price(bookDTO.getPrice())
                .number(bookDTO.getNumber())
                .build();
    }

    public static BookUpdateResponseDTO fromBook(Carti book) {

        return BookUpdateResponseDTO.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .ISBN(book.getISBN())
                .publishPlace(book.getPublishPlace())
                .publishYear(book.getPublishYear())
                .photo(book.getPhoto())
                .genreList(fromGenreList(book.getGenreList()))
                .authorsList(fromAuthorList(book.getAuthorsList()))
                .publishName(book.getPublish().getPublishName())
                .UDK(book.getUDK())
                .BBK(book.getBBK())
                .price(book.getPrice())
                .number(book.getNumber())
                .build();
    }

    public static BookUserResponseDTO fromBookToBookDto(Carti book) {
        return BookUserResponseDTO.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .ISBN(book.getISBN())
                .publishPlace(book.getPublishPlace())
                .publishYear(book.getPublishYear())
                .genreList(getGenreString(book))
                .authorsList(getAuthorString(book))
                .publish(book.getPublish().getPublishName())
                .build();
    }

//    public static BookEmployeeResponseDTO fromBookToEmployeeOrderBookDto(Carti book) {
//        return BookEmployeeResponseDTO.builder()
//                .bookId(book.getBookId())
//                .bookName(book.getBookName())
//                .ISBN(book.getISBN())
//                .publishPlace(book.getPublishPlace())
//                .publishYear(book.getPublishYear())
//                .genreList(getGenreString(book))
//                .authorsList(getAuthorString(book))
//                .publish(book.getPublish().getPublishName())
//                .UDK(book.getUDK())
//                .BBK(book.getBBK())
//                .price(book.getPrice())
//                .build();
//    }

    public static String getAuthorString(Carti book){
        String authors = book.getAuthorsList().stream()
                .map(author -> (author.getFirstName() + " " + author.getLastName()
                        + (author.getSurName() != null ? " " + author.getSurName() : "")).trim())
                .collect(Collectors.joining(", "));

        return authors;
    }

    public static String getGenreString(Carti book){
        String genres = book.getGenreList().stream()
                .map(Genuri::getGenreName)
                .collect(Collectors.joining(", "));

        return genres;
    }
}
