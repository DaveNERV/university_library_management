package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Credential;
import com.projectlicenta.librarymanagementsystem.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findSessionByTokenAndIsExpiredFalse(String token);

    Optional<Session> findSessionByToken(String token);

    boolean existsSessionByIsExpiredTrueAndCredential(Credential credential);

    Session findFirstSessionByCredentialAndIsExpiredTrue(Credential credential);

    boolean existsSessionByCredential(Credential credential);

    Session findSessionByCredential(Credential credential);

    List<Session> findAllByCredential(Credential credential);
}
