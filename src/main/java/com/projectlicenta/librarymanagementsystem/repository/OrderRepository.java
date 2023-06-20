package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Comenzi, Integer>, JpaSpecificationExecutor<Comenzi> {

    List<Comenzi> findAllByReader(Cititori reader);

    List<Comenzi> findAllByReaderAndStatus(Cititori reader, String status);
}
