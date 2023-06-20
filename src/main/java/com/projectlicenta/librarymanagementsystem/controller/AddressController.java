package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.model.requests.AddressDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AddressResponseDTO;
import com.projectlicenta.librarymanagementsystem.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AddressMapper.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseDTO getAddressById(@PathVariable(name = "id") Integer id){
        return fromAddress(addressService.getAddress(id));
    }
}
