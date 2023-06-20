package com.projectlicenta.librarymanagementsystem.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sessionId;

    @ManyToOne
    @JoinColumn(name = "credential_id")
    Credential credential;

    @Column(name = "token")
    String token;

    @Column(name = "is_expired")
    boolean isExpired;

    @Column(name = "expired_date")
    LocalDateTime expiredDate;
}
