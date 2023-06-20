package com.projectlicenta.librarymanagementsystem.model.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="cititori")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cititori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cititor")
    private Integer readerId;

    @Column(name = "grupa")
    private String group;

    @Column(name = "nr_bilet")
    private String ticket;

    @Column(name = "perioada_de_studii")
    private String studyPeriod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_adresa")
    private Adrese address;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<Com> comList;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<Comenzi> orderList;
}
