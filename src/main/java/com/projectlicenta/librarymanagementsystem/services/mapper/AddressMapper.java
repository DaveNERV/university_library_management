package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.requests.AddressDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateEmployeeElementDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UserElementDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.AddressResponseDTO;

import java.util.ArrayList;
import java.util.List;
public class AddressMapper {

    public static Adrese toAddress(AddressDTO addressDTO) {

        return Adrese.builder()
                .city(addressDTO.getCity())
                .street(addressDTO.getStreet())
                .home(addressDTO.getHome())
                .build();
    }

    public static Adrese toAddress(ReaderDTO readerDTO) {

        return Adrese.builder()
                .city(readerDTO.getCity())
                .street(readerDTO.getStreet())
                .home(readerDTO.getHouse())
                .build();
    }

    public static Adrese toAddress(UserElementDTO userElementDTO) {

        return Adrese.builder()
                .city(userElementDTO.getCity())
                .street(userElementDTO.getStreet())
                .home(userElementDTO.getHouse())
                .build();
    }

    public static AddressResponseDTO fromAddress(Adrese address) {

        return AddressResponseDTO.builder()
                .addressId(address.getAdressId())
                .city(address.getCity())
                .street(address.getStreet())
                .build();
    }

    public static List<AddressResponseDTO> fromAddressList(List<Adrese> addressList) {
        List<AddressResponseDTO> addressDtoList = new ArrayList<>();
        addressList.forEach(
                address -> addressDtoList.add(fromAddress(address)));
        return addressDtoList;
    }
}
