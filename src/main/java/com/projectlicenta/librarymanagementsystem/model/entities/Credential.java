package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Credentials")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;

    private String password;

    @OneToOne(mappedBy = "credential")
    private User user;

    public Credential(String password) {
        this.password = password;
    }
}
