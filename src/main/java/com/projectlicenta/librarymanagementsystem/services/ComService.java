package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Genuri;
import com.projectlicenta.librarymanagementsystem.model.responses.ComResponseDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.ComResponseList;

import java.util.List;

public interface ComService {

    ComResponseList addCom(Integer bookId);

    ComResponseList deleteCom(Integer bookId);


    ComResponseList getCom();

    void deleteComByReader(Cititori reader);
}
