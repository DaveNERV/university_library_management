package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateReaderElementDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.DashboardReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.EmployeeUserSearchInOffertResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.ReaderResponseElementDto;
import com.projectlicenta.librarymanagementsystem.model.responses.ReaderElementDto;
import com.projectlicenta.librarymanagementsystem.repository.specifications.ReaderSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReaderMapper {

    public static Specification<Cititori> toReaderSpecification(String ticket, String firstName,
                                                                String lastName, String surName,
                                                                String email) {
        Specification<Cititori> spec = Specification
                .where(ReaderSpecification.hasTicket(ticket))
                .and(ReaderSpecification.hasFirstName(firstName))
                .and(ReaderSpecification.hasLastName(lastName))
                .and(ReaderSpecification.hasSurName(surName))
                .and(ReaderSpecification.hasEmail(email));
        return spec;
    }

    public static Cititori toReader(ReaderDTO readerDTO) {

        return Cititori.builder()
                .group(readerDTO.getGroup())
                .studyPeriod(readerDTO.getStudyPeriod())
                .build();
    }

    public static Cititori toReader(UpdateReaderElementDTO readerDTO) {

        return Cititori.builder()
                .readerId(readerDTO.getReaderId())
                .group(readerDTO.getGroup())
                .studyPeriod(readerDTO.getStudyPeriod())
                .ticket(readerDTO.getTicket())
                .build();
    }

    public static ReaderElementDto fromReaderToElement(Cititori reader) {

        return ReaderElementDto.builder()
                .readerId(reader.getReaderId())
                .email(reader.getUser().getEmail())
                .firstName(reader.getUser().getFirstName())
                .lastName(reader.getUser().getLastName())
                .surName(reader.getUser().getSurName())
                .created(reader.getUser().getCreated())
                .status(reader.getUser().getStatus().toString())
                .role(reader.getUser().getRoleList().get(0).getName())
                .studyPeriod(reader.getStudyPeriod())
                .group(reader.getGroup())
                .ticket(reader.getTicket())
                .phone(reader.getUser().getPhoneNumber())
                .city(reader.getAddress().getCity())
                .street(reader.getAddress().getStreet())
                .house(reader.getAddress().getHome())
                .build();
    }


    public static ReaderResponseElementDto fromReader(Cititori reader) {

        return ReaderResponseElementDto.builder()
                .readerId(reader.getReaderId())
                .email(reader.getUser().getEmail())
                .firstName(reader.getUser().getFirstName())
                .lastName(reader.getUser().getLastName())
                .surName(reader.getUser().getSurName())
                .studyPeriod(reader.getStudyPeriod())
                .group(reader.getGroup())
                .ticket(reader.getTicket())
                .city(reader.getAddress().getCity())
                .street(reader.getAddress().getStreet())
                .house(reader.getAddress().getHome())
                .build();
    }

    public static DashboardReaderDTO fromReaderToDashboard(Cititori cititori) {
        return DashboardReaderDTO.builder()
                .readerId(cititori.getReaderId())
                .fullName(cititori.getUser().getLastName() + " " + cititori.getUser().getFirstName() + " " + cititori.getUser().getSurName())
                .email(cititori.getUser().getEmail())
                .group(cititori.getGroup())
                .ticket(cititori.getTicket())
                .studyPeriod(cititori.getStudyPeriod())
                .phone(cititori.getUser().getPhoneNumber())
                .address(cititori.getAddress().getStreet() + " nr." + cititori.getAddress().getHome() + " or." + cititori.getAddress().getCity())
                .build();
    }
    public static EmployeeUserSearchInOffertResponseDTO fromReaderToReaderInOffer(Cititori reader) {

        return EmployeeUserSearchInOffertResponseDTO.builder()
                .readerId(reader.getReaderId())
                .firstName(reader.getUser().getFirstName())
                .lastName(reader.getUser().getLastName())
                .surName(reader.getUser().getSurName())
                .ticket(reader.getTicket())
                .build();
    }

    public static List<ReaderResponseElementDto> fromReaderList(List<Cititori> readerList) {
        List<ReaderResponseElementDto> readerDtoList = new ArrayList<>();
        readerList.forEach(
                reader -> readerDtoList.add(fromReader(reader)));
        return readerDtoList;
    }

    public static List<EmployeeUserSearchInOffertResponseDTO> fromReaderListToReaderInOfferList(List<Cititori> readerList) {
        List<EmployeeUserSearchInOffertResponseDTO> readerDtoList = new ArrayList<>();
        readerList.forEach(
                reader -> readerDtoList.add(fromReaderToReaderInOffer(reader)));
        return readerDtoList;
    }
}
