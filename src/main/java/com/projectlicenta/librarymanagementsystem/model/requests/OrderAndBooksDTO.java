package com.projectlicenta.librarymanagementsystem.model.requests;

import com.projectlicenta.librarymanagementsystem.model.entities.Carti;
import com.projectlicenta.librarymanagementsystem.model.entities.Cititori;
import com.projectlicenta.librarymanagementsystem.model.entities.Comenzi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAndBooksDTO {

    private Integer bookId;

    private Integer number;
}
