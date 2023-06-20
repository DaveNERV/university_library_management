package com.projectlicenta.librarymanagementsystem.model.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="carti_oferite")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartiOferite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_oferire")
    private Integer offerId;

    @Column(name = "status")
    private String status;

    @Column(name = "data_oferirii")
    private LocalDateTime offerDate;

    @Column(name = "termenul")
    private LocalDateTime deadline;

    @Column(name = "nr_carti")
    private Integer nrExemplary;

    @ManyToOne
    @JoinColumn(name = "id_carte")
    private Carti book;

    @ManyToOne
    @JoinColumn(name = "id_comanda")
    private Comenzi order;
}
