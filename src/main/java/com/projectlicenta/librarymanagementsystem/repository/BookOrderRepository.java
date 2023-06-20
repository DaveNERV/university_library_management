package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.CarteComanda;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookOrderRepository extends JpaRepository<CarteComanda, Integer> {

    List<CarteComanda> findAllByOrder(Comenzi order);
}
