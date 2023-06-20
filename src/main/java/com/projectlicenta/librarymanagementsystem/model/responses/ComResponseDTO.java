package com.projectlicenta.librarymanagementsystem.model.responses;

import com.projectlicenta.librarymanagementsystem.model.requests.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComResponseDTO {

    private BookUserResponseDTO bookDTO;

    private Integer nr;
}
