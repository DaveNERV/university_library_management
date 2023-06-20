package com.projectlicenta.librarymanagementsystem.model.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="angajati")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Angajati{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_angajat")
    private Integer employeeId;

    @Column(name = "IDNP", columnDefinition = "char(13)", nullable = false)
    private String IDNP;

    @Column(name="functia")
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_adresa")
    private Adrese address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Comenzi> orderList;
}
