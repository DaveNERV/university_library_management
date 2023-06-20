package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="carti")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carte")
    private Integer bookId;

    @Column(name = "denumire_carte")
    private String bookName;

    @Column(name = "isbn")
    private String ISBN;

    @Column(name = "semn_autor", columnDefinition = "char(3)")
    private String copyrightSign;

    @Column(name = "loc_editurii")
    private String publishPlace;

    @Column(name = "anul_editurii")
    private Short publishYear;

    @Column(name = "foto")
    private String photo;

    @Column(name = "udk")
    private String UDK;

    @Column(name = "bbk")
    private String BBK;

    @Column(name = "pret")
    private Integer price;

    @Column(name = "numar")
    private Integer number;

    @ManyToMany(mappedBy = "bookList")
    private List<Genuri> genreList;

    @ManyToMany(mappedBy = "bookList")
    private List<Autori> authorsList;

    @ManyToOne
    @JoinColumn(name = "id_editura")
    private Editura publish;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<CarteComanda> bookCommandList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<CartiOferite> offeredBookList;
}

