package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.AddressNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.repository.AuthorRepository;
import com.projectlicenta.librarymanagementsystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Autori addAuthor(Autori author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Autori> getAuthorList() {
        return authorRepository.findAll();
    }

    @Override
    public void updateAuthor(Integer authorId, Autori author) {
        Autori findedAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + authorId  + " was not found"));

        findedAuthor.setFirstName(author.getFirstName());
        findedAuthor.setLastName(author.getLastName());
        findedAuthor.setSurName(author.getSurName());

        authorRepository.save(findedAuthor);
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        Autori findedAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + authorId  + " was not found"));
        authorRepository.deleteById(findedAuthor.getAuthorId());
    }
}
