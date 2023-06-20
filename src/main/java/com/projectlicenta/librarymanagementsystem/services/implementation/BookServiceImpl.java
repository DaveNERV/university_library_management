package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.*;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.model.responses.AuthorListDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AuthorResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.BookEmployeeResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;
import com.projectlicenta.librarymanagementsystem.repository.AuthorRepository;
import com.projectlicenta.librarymanagementsystem.repository.BookRepository;
import com.projectlicenta.librarymanagementsystem.repository.GenreRepository;
import com.projectlicenta.librarymanagementsystem.repository.PublishRepository;
import com.projectlicenta.librarymanagementsystem.repository.specifications.BookSpecification;
import com.projectlicenta.librarymanagementsystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.projectlicenta.librarymanagementsystem.services.mapper.BookMapper.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final PublishRepository publishRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public Carti getBook(Integer bookId){
        Carti findedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId  + " was not found"));
        return findedBook;
    }

    @Transactional
    @Override
    public void addBook(Carti book, String publishName, List<Integer> genreList, List<Integer> authorsList) {
        Editura publish = publishRepository.findEdituraByPublishNameIgnoreCase(publishName)
                .orElseGet(() -> {
                    Editura newPublish = Editura.builder().publishName(publishName).build();
                    return publishRepository.save(newPublish);
                });

        List<Genuri> genres = genreList.stream()
                .map(genreId -> genreRepository.findById(genreId)
                        .orElseThrow(() -> new InvalidGenreException("Invalid genre provided")))
                .collect(Collectors.toList());

        List<Autori> authors = authorsList.stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new InvalidAuthorException("Invalid author provided")))
                .collect(Collectors.toList());

        authors.forEach(author -> author.getBookList().add(book));
        genres.forEach(genre -> genre.getBookList().add(book));

        book.setCopyrightSign(generateCopyrightSign(authors.stream().findFirst().get().getLastName()));
        book.setGenreList(genres);
        book.setAuthorsList(authors);
        book.setPublish(publish);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(Integer bookId, Carti book, String publishName,
                           List<Integer> genreList, List<Integer> authorsList) {
        Carti findedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId  + " was not found"));
        Editura publish = publishRepository.findEdituraByPublishNameIgnoreCase(publishName)
                .orElseGet(() -> {
                    Editura newPublish = Editura.builder().publishName(publishName).build();
                    return publishRepository.save(newPublish);
                });

        List<Genuri> genres = genreList.stream()
                .map(genreId -> genreRepository.findById(genreId)
                        .orElseThrow(() -> new InvalidGenreException("Invalid genre provided")))
                .collect(Collectors.toList());

        List<Autori> authors = authorsList.stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new InvalidAuthorException("Invalid author provided")))
                .collect(Collectors.toList());

        findedBook.setBookName(book.getBookName());
        findedBook.setPublish(publish);
        findedBook.setISBN(book.getISBN());
        findedBook.setPhoto(book.getPhoto());
        findedBook.setCopyrightSign(generateCopyrightSign(authors.stream().findFirst().get().getLastName()));
        findedBook.setPublishPlace(book.getPublishPlace());
        findedBook.setPublishYear(book.getPublishYear());
        findedBook.setUDK(book.getUDK());
        findedBook.setBBK(book.getBBK());

        // Remove the book from the old genres and authors and save them
        findedBook.getGenreList().forEach(genre -> {
            genre.getBookList().remove(findedBook);
            genreRepository.save(genre);
        });
        findedBook.getAuthorsList().forEach(author -> {
            author.getBookList().remove(findedBook);
            authorRepository.save(author);
        });

        // Set the new genres and authors and save the book
        findedBook.setGenreList(genres);
        findedBook.setAuthorsList(authors);
        bookRepository.save(findedBook);

        // Add the book to the new genres and authors and save them
        genres.forEach(genre -> {
            genre.getBookList().add(findedBook);
            genreRepository.save(genre);
        });
        authors.forEach(author -> {
            author.getBookList().add(findedBook);
            authorRepository.save(author);
        });
    }

    @Override
    public List<BookEmployeeResponseDTO> getbookList(Specification<Carti> spec) {

        List<Carti> books = bookRepository.findAll(spec);

        return books.stream()
                .map(book -> {

                    String authors = getAuthorString(book);
                    String genres = getGenreString(book);

                    return BookEmployeeResponseDTO.builder()
                            .bookId(book.getBookId())
                            .bookName(book.getBookName())
                            .ISBN(book.getISBN())
                            .copyrightSign(book.getCopyrightSign())
                            .publishPlace(book.getPublishPlace())
                            .publishYear(book.getPublishYear())
                            .genreList(genres)
                            .authorsList(authors)
                            .publish(book.getPublish().getPublishName())
                            .UDK(book.getUDK())
                            .BBK(book.getBBK())
                            .price(book.getPrice())
                            .nrBooks(book.getNumber())
                            .build();
                }).collect(Collectors.toList());
    }

    private String generateCopyrightSign(String lastName) {
        String firstLetter = lastName.substring(0, 1).toUpperCase();

        String secondLetter = lastName.substring(1, 2).toUpperCase();
        String thirdLetter = lastName.substring(2, 3).toUpperCase();

        List<String> combinations = new ArrayList<>();
        for (char i = 'A'; i <= 'Z'; i++) {
            for (char j = 'A'; j <= 'Z'; j++) {
                combinations.add("" + i + j);
            }
        }

        int index = combinations.indexOf(secondLetter + thirdLetter) + 1;

        String authorSign = String.format("%s%02d", firstLetter, index);

        return authorSign;
    }

    @Override
    public void deleteBook(Integer bookId){
        Carti findedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId  + " was not found"));

        // Remove the book from each author's list of books
        findedBook.getAuthorsList().forEach(author -> {
            author.getBookList().remove(findedBook);
        });

        // Remove the book from each genre's list of books
        findedBook.getGenreList().forEach(genre -> {
            genre.getBookList().remove(findedBook);
        });

        // Save the authors and genres after removal of the book
        authorRepository.saveAll(findedBook.getAuthorsList());
        genreRepository.saveAll(findedBook.getGenreList());

        bookRepository.deleteById(findedBook.getBookId());
    }
}
