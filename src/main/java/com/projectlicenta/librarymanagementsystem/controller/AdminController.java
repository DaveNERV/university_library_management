package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Status;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateEmployeeElementDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateReaderElementDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.DashboardEmployeeDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeResponseElementDto;
import com.projectlicenta.librarymanagementsystem.model.responses.ReaderElementDto;
import com.projectlicenta.librarymanagementsystem.services.EmployeeService;
import com.projectlicenta.librarymanagementsystem.services.ReaderService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AddressMapper.toAddress;
import static com.projectlicenta.librarymanagementsystem.services.mapper.EmployeeMapper.*;
import static com.projectlicenta.librarymanagementsystem.services.mapper.ReaderMapper.*;
import static com.projectlicenta.librarymanagementsystem.services.mapper.UserMapper.toUser;

@Controller
@RequestMapping(value="/admin/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final EmployeeService employeeService;

    private final UserService userService;

    private final ReaderService readerService;

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("currentUser", fromEmployeeToElement(employeeService.loadCurrentEmployee()));
        return "admin/admin-home";
    }

    @GetMapping("manage-reader-accounts")
    public String manageReaders(@RequestParam(required = false) String ticket,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String surName,
                                @RequestParam(required = false) String email,
                                Model model) {
        List<Cititori> readers = readerService.getReaderList(toReaderSpecification(ticket, firstName, lastName, surName, email));

        model.addAttribute("readers", fromReaderList(readers));

        model.addAttribute("ticket", ticket);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("surName", surName);
        model.addAttribute("email", email);

        return "admin/admin-manage-readers-accounts";
    }

    @GetMapping("manage-employee-accounts")
    public String manageEmployee(@RequestParam(required = false) String jobTitle,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String surName,
                                @RequestParam(required = false) String email,
                                Model model) {
        List<Angajati> employees = employeeService.getEmployeeList(toEmployeeSpecification(jobTitle, firstName, lastName, surName, email));

        model.addAttribute("employees", fromEmployeeList(employees));

        model.addAttribute("jobTitle", jobTitle);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("surName", surName);
        model.addAttribute("email", email);

        return "admin/admin-manage-employe-accounts";
    }

    @GetMapping(value="manage-employe-account")
    public String manageEmployeeAccount(@RequestParam Integer employeeId,
                                Model model) {
        EmployeeResponseElementDto employee = fromEmployeeToElement(employeeService.getEmployee(employeeId));
        model.addAttribute("employee", employee);
        model.addAttribute("updateEmployee", new UpdateEmployeeElementDTO());
        return "admin/admin-manage-employe-account";
    }

    @GetMapping(value="manage-reader-account")
    public String manageReaderAccount(@RequestParam String ticket,
                                Model model) {
        ReaderElementDto reader = fromReaderToElement(readerService.getReader(ticket));
        model.addAttribute("reader", reader);
        model.addAttribute("updateReader", new UpdateReaderElementDTO());
        return "admin/admin-manage-reader-account";
    }

    @PutMapping(value="confirmreaderaccountsettings")
    public String saveAccountChanges(@ModelAttribute @Valid UpdateReaderElementDTO updateReader) {

        User user = toUser(updateReader);
        user.setStatus(updateReader.getStatus());

        readerService.updateReader(toReader(updateReader), user, updateReader.getPassword(), toAddress(updateReader));

        return "redirect:manage-reader-accounts";
    }

    @PutMapping(value="confirmemployeeaccountsettings")
    public String saveAccountChanges(@ModelAttribute @Valid UpdateEmployeeElementDTO updateEmployee) {

        User user = toUser(updateEmployee);
        user.setStatus(updateEmployee.getStatus());

        employeeService.updateEmployee(toEmployee(updateEmployee), user, updateEmployee.getPassword(), toAddress(updateEmployee));

        return "redirect:manage-employee-accounts";
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public DashboardEmployeeDTO getReaderFromContext() {
        Angajati employee = employeeService.loadCurrentEmployee();
        DashboardEmployeeDTO dashboardEmployeeDTO = fromEmployeeToDashboard(employee);
        return dashboardEmployeeDTO;
    }
}
