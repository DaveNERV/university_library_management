package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;

import java.util.List;

public interface AuthorService {

    Autori addAuthor(Autori author);

    List<Autori> getAuthorList();

    void updateAuthor(Integer authorId, Autori author);

    void deleteAuthor(Integer authorId);
}
