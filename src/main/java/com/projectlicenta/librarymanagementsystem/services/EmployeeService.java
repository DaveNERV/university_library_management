package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Angajati employee, User user, String type, String password);

    void updateEmployee(Angajati employee, User user, String password, Adrese address);

    Angajati getEmployee(Integer employeeId);

    List<Angajati> getEmployeeList(Specification<Angajati> spec);

    void deleteEmployee(Integer employeeId);

    public Angajati loadCurrentEmployee();
}
