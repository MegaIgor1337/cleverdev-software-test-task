package com.lam.migrationservice.service;

import com.lam.migrationservice.dto.ClientResponseDto;
import com.lam.migrationservice.dto.NoteRequestDto;
import com.lam.migrationservice.dto.NoteResponseDto;

import java.util.List;
import java.util.Set;

public interface WebService {
    List<ClientResponseDto> findClients(Set<String> oldClientGuids);
    List<NoteResponseDto> findNotes(List<NoteRequestDto> requests);
    List<NoteRequestDto> createNoteRequests(List<ClientResponseDto> clients);
}
