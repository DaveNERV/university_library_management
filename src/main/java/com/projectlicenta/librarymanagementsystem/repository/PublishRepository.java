package com.projectlicenta.librarymanagementsystem.repository;

import com.projectlicenta.librarymanagementsystem.model.entities.Editura;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishRepository extends JpaRepository<Editura, Integer> {


    Optional<Editura> findEdituraByPublishNameIgnoreCase(String publishName);
}
