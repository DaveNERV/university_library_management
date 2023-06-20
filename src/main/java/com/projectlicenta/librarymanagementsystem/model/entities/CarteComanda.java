package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="carte_in_comanda")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarteComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carte_comanda")
    private Integer bookComandId;

    @Column(name = "numar")
    private Integer number;

    @Column(name = "costul")
    private Integer price;

    @Column(name = "anulat")
    private Boolean canceled;

    @ManyToOne
    @JoinColumn(name = "id_carte")
    private Carti book;

    @ManyToOne
    @JoinColumn(name = "id_comanda")
    private Comenzi order;
}
