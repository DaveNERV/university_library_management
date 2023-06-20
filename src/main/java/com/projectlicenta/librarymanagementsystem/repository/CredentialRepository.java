package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findCredentialByUserEmailContaining(String email);

    Optional<Credential> findCredentialByUserId(Long userId);
}
