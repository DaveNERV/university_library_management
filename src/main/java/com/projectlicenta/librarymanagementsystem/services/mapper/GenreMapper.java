package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.model.requests.AddressDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.GenreDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AddressResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.GenreResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class GenreMapper {

    public static Genuri toGenre(GenreDTO genreDTO) {

        return Genuri.builder()
                .genreName(genreDTO.getGenreName())
                .build();
    }

    public static GenreResponseDTO fromGenre(Genuri genre) {

        return GenreResponseDTO.builder()
                .genreId(genre.getGenreId())
                .genreName(genre.getGenreName())
                .build();
    }

    public static List<GenreResponseDTO> fromGenreList(List<Genuri> genreList) {
        List<GenreResponseDTO> genreDtoList = new ArrayList<>();
        genreList.forEach(
                genre -> genreDtoList.add(fromGenre(genre)));
        return genreDtoList;
    }
}
