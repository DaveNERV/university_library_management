package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Adrese;
import com.projectlicenta.librarymanagementsystem.model.entities.Angajati;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReaderService {

    void addReader(Cititori reader, User user, String type, String password, Adrese address);

    void updateReader(Cititori reader, User user, String password, Adrese address);

    Cititori getReader(String ticket);

    List<Cititori> getReaderList();

    List<Cititori> getReaderList(Specification<Cititori> spec);

    Cititori getDashboard();
}
