package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.repository.specifications.BookSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Carti, Integer>, JpaSpecificationExecutor<Carti> {


//        @Query(value ="SELECT * " +
//                "FROM carti c INNER JOIN exemplare e ON e.id_carte = c.id_carte " +
//                "WHERE DATE(c.data_CSU) = :CSUDate " +
//                "AND c.CSU = :CSU ",
//                nativeQuery = true)
//        List<Carti> getListOfBooksByCSUDateAndCSU(@Param("CSUDate") LocalDate CSUDate,
//                                                @Param("CSU") String CSU);
}
