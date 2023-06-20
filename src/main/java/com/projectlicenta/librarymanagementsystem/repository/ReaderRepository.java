package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Cititori, Integer>, JpaSpecificationExecutor<Cititori> {

    Optional<Cititori> findCititoriByTicketIgnoreCase(String ticket);

    boolean existsCititoriByTicketIgnoreCaseAndReaderIdNot(String ticket, Integer readerId);

    boolean existsCititoriByTicketIgnoreCase(String ticket);

    Optional<Cititori> findCititoriByUser(User user);

    @Query(value ="SELECT MAX(nr_bilet) FROM cititori", nativeQuery = true)
    Optional<String> findMaxTicket();
}
