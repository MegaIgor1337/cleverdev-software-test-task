package com.lam.migrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDto {
    private String agency;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String clientGuid;
}
