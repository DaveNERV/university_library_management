package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.*;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.repository.ReaderRepository;
import com.projectlicenta.librarymanagementsystem.services.AddressService;
import com.projectlicenta.librarymanagementsystem.services.ReaderService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Address;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderSeviceImpl implements ReaderService {

    private final ReaderRepository readerRepository;

    private final UserService userService;

    private final AddressService addressService;

    @Transactional
    @Override
    public void addReader(Cititori reader, User user, String type, String password, Adrese address){
        String ticket = generateTicketByOrganization();
        if (existsUserByTicket(ticket)){
            throw new UserAlreadyRegisteredException("Ticket: " + ticket + " is already registered.");
        }

        User addedUser = userService.addUser(user, type, password);

        Adrese savedAddress = addressService.addAddress(address);
        reader.setUser(addedUser);
        reader.setTicket(ticket);
        reader.setAddress(savedAddress);

        readerRepository.save(reader);
    }

    @Transactional
    @Override
    public void updateReader(Cititori reader, User user, String password, Adrese address){
        String ticket = reader.getTicket();
        Integer readerId = reader.getReaderId();

        Cititori findedReader = readerRepository.findById(readerId)
                .orElseThrow(() -> new ReaderNotFoundException("Reader  with id " + readerId  + " was not found"));
        if (existsUserByTicket(ticket, findedReader.getReaderId())){
            throw new UserAlreadyRegisteredException("Ticket: " + ticket + " is already registered.");
        }
        User findedUser = findedReader.getUser();
        userService.updateUser(findedUser, user, password);

        Adrese findedAddress = findedReader.getAddress();

        addressService.updateAddress(address, findedAddress);

        findedReader.setTicket(ticket);
        findedReader.setStudyPeriod(reader.getStudyPeriod());
        findedReader.setGroup(reader.getGroup());

        readerRepository.save(findedReader);
    }

    private boolean existsUserByTicket(String ticket, Integer readerId) {
        return readerRepository.existsCititoriByTicketIgnoreCaseAndReaderIdNot(ticket, readerId);
    }

    private boolean existsUserByTicket(String ticket) {
        return readerRepository.existsCititoriByTicketIgnoreCase(ticket);
    }

    @Transactional(readOnly = true)
    @Override
    public Cititori getReader(String ticket){
        Cititori findedReader = readerRepository.findCititoriByTicketIgnoreCase(ticket)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with ticket " + ticket  + " was not found"));
        return findedReader;
    }

    private String generateTicketByOrganization(){
        Optional<String> findedTicket = readerRepository.findMaxTicket();
        String prefix, numberPart, ticket;

        if(findedTicket.isPresent()) {
            ticket = findedTicket.get();
            prefix = ticket.replaceAll("\\d", "");
            numberPart = ticket.replaceAll("\\D", "");
        }else{
            prefix = "U";
            numberPart = "1";
        }

        int incrementedNumber = Integer.parseInt(numberPart) + 1;
        return prefix + incrementedNumber;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cititori> getReaderList(Specification<Cititori> spec){
        return readerRepository.findAll(spec);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cititori> getReaderList(){
        return readerRepository.findAll();
    }

    @Override
    public Cititori getDashboard(){
        User user = userService.loadCurrentUser();
        Cititori findedReader = readerRepository.findCititoriByUser(user)
                .orElseThrow(() -> new ReaderNotFoundException("Reader  with userId " + user.getId()  + " was not found"));
        return findedReader;
    }
}
