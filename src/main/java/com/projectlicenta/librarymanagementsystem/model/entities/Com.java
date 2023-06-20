package com.projectlicenta.librarymanagementsystem.model.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name="com")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Com {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_com")
    private Integer comId;

    @ManyToOne
    @JoinColumn(name = "id_carte")
    private Carti book;

    @ManyToOne
    @JoinColumn(name = "id_cititor")
    private Cititori reader;

    @Column(name="nr")
    private Integer nr;
}
