package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.requests.EmployeeDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateEmployeeElementDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.DashboardEmployeeDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.DashboardReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeResponseDto;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeResponseElementDto;
import com.projectlicenta.librarymanagementsystem.repository.specifications.EmployeeSpecification;
import com.projectlicenta.librarymanagementsystem.repository.specifications.ReaderSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static Specification<Angajati> toEmployeeSpecification(String jobTitle, String firstName,
                                                                String lastName, String surName,
                                                                String email) {
        Specification<Angajati> spec = Specification
                .where(EmployeeSpecification.hasJobTitle(jobTitle))
                .and(EmployeeSpecification.hasFirstName(firstName))
                .and(EmployeeSpecification.hasLastName(lastName))
                .and(EmployeeSpecification.hasSurName(surName))
                .and(EmployeeSpecification.hasEmail(email));
        return spec;
    }

    public static Angajati toEmployee(EmployeeDTO employeeDTO) {

        return Angajati.builder()
                .IDNP(employeeDTO.getIDNP())
                .jobTitle(employeeDTO.getJobTitle())
                .build();
    }

    public static Angajati toEmployee(UpdateEmployeeElementDTO employeeDTO) {

        return Angajati.builder()
                .employeeId(employeeDTO.getEmployeeId())
                .IDNP(employeeDTO.getIDNP())
                .jobTitle(employeeDTO.getJobTitle())
                .build();
    }

    public static EmployeeResponseElementDto fromEmployeeToElement(Angajati employee) {
        return EmployeeResponseElementDto.builder()
                .employeeId(employee.getEmployeeId())
                .email(employee.getUser().getEmail())
                .firstName(employee.getUser().getFirstName())
                .lastName(employee.getUser().getLastName())
                .surName(employee.getUser().getSurName())
                .phone(employee.getUser().getPhoneNumber())
                .icon(employee.getUser().getIcon())
                .created(employee.getUser().getCreated())
                .IDNP(employee.getIDNP())
                .jobTitle(employee.getJobTitle())
                .status(employee.getUser().getStatus().toString())
                .role(employee.getUser().getRoleList().get(0).getName())
                .house(employee.getAddress().getHome())
                .city(employee.getAddress().getCity())
                .street(employee.getAddress().getStreet())
                .build();
    }

    public static EmployeeResponseDto fromEmployee(Angajati employee) {
        return EmployeeResponseDto.builder()
                .employeeId(employee.getEmployeeId())
                .email(employee.getUser().getEmail())
                .firstName(employee.getUser().getFirstName())
                .lastName(employee.getUser().getLastName())
                .surName(employee.getUser().getSurName())
                .icon(employee.getUser().getIcon())
                .jobTitle(employee.getJobTitle())
                .house(employee.getAddress().getHome())
                .city(employee.getAddress().getCity())
                .street(employee.getAddress().getStreet())
                .build();
    }

    public static DashboardEmployeeDTO fromEmployeeToDashboard(Angajati employee) {
        return DashboardEmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .fullName(employee.getUser().getLastName() + " " + employee.getUser().getFirstName() + " " + employee.getUser().getSurName())
                .email(employee.getUser().getEmail())
                .phone(employee.getUser().getPhoneNumber())
                .jobTitle(employee.getJobTitle())
                .IDNP(employee.getIDNP())
                .address(employee.getAddress().getStreet() + " nr." + employee.getAddress().getHome() + " or." + employee.getAddress().getCity())
                .build();
    }

    public static List<EmployeeResponseDto> fromEmployeeList(List<Angajati> employeeList) {
        List<EmployeeResponseDto> employeeDtoList = new ArrayList<>();
        employeeList.forEach(
                employee -> employeeDtoList.add(fromEmployee(employee)));
        return employeeDtoList;
    }
}
