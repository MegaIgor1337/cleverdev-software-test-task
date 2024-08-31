package com.lam.migrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalNotesResponse {
    private List<NoteResponseDto> noteResponseDtoList;
}
