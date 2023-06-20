package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Value
@Builder
@AllArgsConstructor
public class ErrorResponse {
    LocalDateTime timestamp = LocalDateTime.now();
    int status;
    String error;
    String message;
    String path;
}
