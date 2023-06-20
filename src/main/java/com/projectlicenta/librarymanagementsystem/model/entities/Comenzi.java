package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="comenzi")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comenzi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_comanda")
    private Integer orderId;

    @Column(name = "status", columnDefinition = "VARCHAR(15) DEFAULT 'se prelucreaza'")
    private String status;

    @Column(name = "data_comenzii")
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "id_cititor")
    private Cititori reader;

    @ManyToOne
    @JoinColumn(name = "id_angajat")
    private Angajati employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CarteComanda> commandList;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartiOferite> offeredBookList;
}
