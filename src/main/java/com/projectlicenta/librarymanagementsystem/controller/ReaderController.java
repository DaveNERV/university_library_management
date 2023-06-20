package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.exceptions.UserAlreadyRegisteredException;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.DashboardReaderDTO;
import com.projectlicenta.librarymanagementsystem.services.ReaderService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AddressMapper.toAddress;
import static com.projectlicenta.librarymanagementsystem.services.mapper.ReaderMapper.*;
import static com.projectlicenta.librarymanagementsystem.services.mapper.UserMapper.toUser;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderService readerService;

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReader(@RequestBody @Valid ReaderDTO readerDTO) {
        String email = readerDTO.getEmail();
        if (userService.existsUserByEmail(email)){
            throw new UserAlreadyRegisteredException("Email: " + email + " is already registered.");
        }
        readerService.addReader(toReader(readerDTO), toUser(readerDTO), readerDTO.getType(), readerDTO.getPassword(), toAddress(readerDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DashboardReaderDTO getReaderFromContext() {
        Cititori reader = readerService.getDashboard();
        DashboardReaderDTO dashboardReaderDTO = fromReaderToDashboard(reader);
        return dashboardReaderDTO;
    }
}
