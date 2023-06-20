package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.entities.Autori;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Autori, Integer> {
}
