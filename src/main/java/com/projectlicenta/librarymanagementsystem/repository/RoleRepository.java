package com.projectlicenta.librarymanagementsystem.repository;


import com.projectlicenta.librarymanagementsystem.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
