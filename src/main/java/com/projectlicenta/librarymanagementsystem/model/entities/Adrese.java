package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="adrese")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adrese {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_adresa")
    private Integer adressId;

    @Column(name = "strada")
    private String street;

    @Column(name = "oras")
    private String city;

    @Column(name = "casa")
    private String home;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Angajati> employeeList;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Cititori> readerList;
}
