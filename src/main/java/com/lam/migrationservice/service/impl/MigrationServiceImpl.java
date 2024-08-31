package com.lam.migrationservice.service.impl;

import com.lam.migrationservice.dto.ClientResponseDto;
import com.lam.migrationservice.dto.NoteRequestDto;
import com.lam.migrationservice.dto.NoteResponseDto;
import com.lam.migrationservice.model.Note;
import com.lam.migrationservice.model.Patient;
import com.lam.migrationservice.model.User;
import com.lam.migrationservice.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
public class MigrationServiceImpl implements MigrationService {
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private WebService webService;


    @Override
    public void importNotes() {
        List<Patient> allActivePatients = patientService.findAllActivePatients();

        Set<String> oldClientGuids = extractOldClientGuids(allActivePatients);

        List<ClientResponseDto> clients = webService.findClients(oldClientGuids);

        List<NoteRequestDto> noteRequests = webService.createNoteRequests(clients);

        List<NoteResponseDto> noteResponseDtoList = webService.findNotes(noteRequests);

        if (!noteResponseDtoList.isEmpty()) {

            Set<String> noteLogins = noteResponseDtoList.stream()
                    .map(NoteResponseDto::getLoggedUser)
                    .collect(Collectors.toSet());

            Map<String, User> loginUserMap = userService.getLoginUserMap(noteLogins);

            oldClientGuids = noteResponseDtoList.stream()
                    .map(NoteResponseDto::getClientGuid)
                    .collect(Collectors.toSet());

            Map<String, Patient> oldGuidPatientMap = mapPatientsByOldGuid(allActivePatients, oldClientGuids);

            List<String> noteGuids = noteResponseDtoList.stream()
                    .map(NoteResponseDto::getGuid)
                    .toList();

            Map<String, Note> alreadyExistNotesMap = noteService.getNotesByGuids(noteGuids).stream()
                    .collect(Collectors.toMap(Note::getGuid, Function.identity()));

            processAndSaveNotes(noteResponseDtoList, alreadyExistNotesMap, oldGuidPatientMap, loginUserMap);
        }
    }


    private Set<String> extractOldClientGuids(List<Patient> allActivePatients) {
        return allActivePatients.stream()
                .map(Patient::getOldClientGuid)
                .flatMap(guids -> Stream.of(guids.split(",")))
                .map(String::trim)
                .collect(Collectors.toSet());
    }


    private Map<String, Patient> mapPatientsByOldGuid(List<Patient> patients, Set<String> guids) {
        return patients.stream()
                .filter(patient -> Arrays.stream(patient.getOldClientGuid().split(","))
                        .map(String::trim)
                        .anyMatch(guids::contains))
                .flatMap(patient -> Arrays.stream(patient.getOldClientGuid().split(","))
                        .map(guid -> new AbstractMap.SimpleEntry<>(guid.trim(), patient)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private void processAndSaveNotes(List<NoteResponseDto> noteResponseDtoList,
                                     Map<String, Note> alreadyExistNotes,
                                     Map<String, Patient> patientMap,
                                     Map<String, User> userMap) {

        List<Note> notesToSave = noteResponseDtoList.stream().map(noteDto -> {

            Patient patient = patientMap.get(noteDto.getClientGuid());

            User user = userMap.get(noteDto.getLoggedUser());

            Note note = alreadyExistNotes.get(noteDto.getGuid());

            if (note != null) {
                if (noteDto.getModifiedDateTime() != null &&
                        note.getLastModifiedDateTime() != null &&
                        noteDto.getModifiedDateTime().isAfter(note.getLastModifiedDateTime())) {
                    note.setLastModifiedDateTime(noteDto.getModifiedDateTime());
                    note.setNote(noteDto.getComments());
                }
                return note;
            } else {
                return getNewNote(noteDto, patient, user);
            }
        }).collect(Collectors.toList());

        noteService.saveAllNotes(notesToSave);
    }

    private Note getNewNote(NoteResponseDto noteDto, Patient patient, User user) {
        Note newNote = new Note();
        newNote.setGuid(noteDto.getGuid());
        newNote.setPatient(patient);
        if (user != null) {
            newNote.setCreatedUser(user);
            newNote.setLastModifiedUser(user);
        } else {
            User newUser = new User();
            newUser.setLogin(noteDto.getLoggedUser());
            newNote.setCreatedUser(newUser);
            newNote.setLastModifiedUser(newUser);
        }
        newNote.setNote(noteDto.getComments());
        newNote.setCreatedDateTime(noteDto.getCreatedDateTime());
        newNote.setLastModifiedDateTime(noteDto.getModifiedDateTime());
        return newNote;
    }
}
