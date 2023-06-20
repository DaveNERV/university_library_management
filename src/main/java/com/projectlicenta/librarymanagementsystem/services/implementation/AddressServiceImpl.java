package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.AddressNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.BranchNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.ReaderNotFoundException;
import com.projectlicenta.librarymanagementsystem.exceptions.UserAlreadyRegisteredException;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.repository.AddressRepository;
import com.projectlicenta.librarymanagementsystem.services.AddressService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    @Override
    public Adrese addAddress(Adrese address) {
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public void updateAddress(Adrese address, Adrese findedAddress) {
        findedAddress.setCity(address.getCity());
        findedAddress.setStreet(address.getStreet());
        findedAddress.setHome(address.getHome());

        addressRepository.save(findedAddress);
    }

    @Transactional(readOnly = true)
    @Override
    public Adrese getAddress(Integer addressId) {
        Adrese findedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new BranchNotFoundException("Address with id " + addressId  + " was not found"));
        return findedAddress;
    }

    @Transactional
    @Override
    public void deleteAddress(Integer addressId) {
        Adrese findedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address with id " + addressId  + " was not found"));
        addressRepository.deleteById(findedAddress.getAdressId());
    }
}
