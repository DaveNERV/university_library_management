package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;

import java.util.List;

public interface AddressService {

    Adrese addAddress(Adrese address);

    void updateAddress(Adrese address, Adrese findedAddress);

    Adrese getAddress(Integer addressId);

    void deleteAddress(Integer addressId);
}
