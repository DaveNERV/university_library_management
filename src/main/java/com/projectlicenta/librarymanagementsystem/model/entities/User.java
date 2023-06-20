package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "prenume")
    private String firstName;

    @Column(name = "nume")
    private String lastName;

    @Column(name = "patronimic")
    private String surName;

    @Column(name = "icon")
    private String icon;

    @Column(name = "phone")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id")
    private Credential credential;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Angajati> employeeList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cititori> readerList;
}
