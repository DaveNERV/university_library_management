package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.model.requests.AuthorDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.GenreDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AuthorResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {

    public static Autori toAuthor(AuthorDTO authorDTO) {

        return Autori.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .surName(authorDTO.getSurName())
                .build();
    }

    public static AuthorResponseDTO fromAuthor(Autori author) {

        return AuthorResponseDTO.builder()
                .authorId(author.getAuthorId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .surName(author.getSurName())
                .build();
    }

    public static List<AuthorResponseDTO> fromAuthorList(List<Autori> authorList) {
        List<AuthorResponseDTO> authorDtoList = new ArrayList<>();
        authorList.forEach(
                author -> authorDtoList.add(fromAuthor(author)));
        return authorDtoList;
    }
}
