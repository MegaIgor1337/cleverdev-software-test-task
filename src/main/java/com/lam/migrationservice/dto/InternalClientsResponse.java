package com.lam.migrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalClientsResponse {
    private List<ClientResponseDto> clientResponseDtoList;
}
