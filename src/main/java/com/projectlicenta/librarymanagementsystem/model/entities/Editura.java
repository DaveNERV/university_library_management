package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="edituri")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_editura")
    private Integer publishId;

    @Column(name = "nume_editura")
    private String publishName;

    @OneToMany(mappedBy = "publish", cascade = CascadeType.ALL)
    private List<Carti> bookList;
}
