package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="genuri")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genuri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_gen")
    private Integer genreId;

    @Column(name = "denumire_gen")
    private String genreName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "genuri_cartilor", joinColumns = @JoinColumn(name = "id_gen"),
            inverseJoinColumns = @JoinColumn(name = "id_carte"))
    private List<Carti> bookList;
}
