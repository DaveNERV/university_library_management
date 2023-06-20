package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="autori")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer authorId;

    @Column(name = "nume_autor")
    private String lastName;

    @Column(name = "prenume_autor")
    private String firstName;

    @Column(name = "patronimic_autor")
    private String surName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "autorii_cartilor", joinColumns = @JoinColumn(name = "id_autor"),
            inverseJoinColumns = @JoinColumn(name = "id_carte"))
    private List<Carti> bookList;
}
