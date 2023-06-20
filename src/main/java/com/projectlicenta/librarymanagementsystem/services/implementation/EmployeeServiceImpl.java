package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.EmployeeNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.InvalidAddressException;
import com.projectlicenta.librarymanagementsystem.exceptions.InvalidBranchException;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.repository.AddressRepository;
import com.projectlicenta.librarymanagementsystem.repository.EmployeeRepository;
import com.projectlicenta.librarymanagementsystem.services.AddressService;
import com.projectlicenta.librarymanagementsystem.services.EmployeeService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    private final AddressService addressService;

    @Override
    public void addEmployee(Angajati employee, User user, String type, String password){
        User addedUser = userService.addUser(user, type, password);

        employee.setUser(addedUser);

        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(Angajati employee, User user, String password, Adrese address){
        Integer employeeId = employee.getEmployeeId();

        Angajati findedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee  with id " + employeeId  + " was not found"));

        userService.updateUser(findedEmployee.getUser(), user, password);

        addressService.updateAddress(address, findedEmployee.getAddress());

        findedEmployee.setIDNP(employee.getIDNP());
        findedEmployee.setJobTitle(employee.getJobTitle());

        employeeRepository.save(findedEmployee);
    }

    @Override
    public Angajati getEmployee(Integer employeeId){
        Angajati findedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + employeeId  + " was not found"));
        return findedEmployee;
    }

    @Override
    public List<Angajati> getEmployeeList(Specification<Angajati> spec){
        return employeeRepository.findAll(spec);
    }

    @Transactional
    @Override
    public void deleteEmployee(Integer employeeId) {
        Angajati findedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee  with id " + employeeId  + " was not found"));

        userService.deleteUser(findedEmployee.getUser());
    }

    @Override
    public Angajati loadCurrentEmployee(){
        User user = userService.loadCurrentUser();
        Angajati findedEmployee = employeeRepository.findAngajatiByUser(user)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee  with userId " + user.getId()  + " was not found"));
        return findedEmployee;
    }
}
