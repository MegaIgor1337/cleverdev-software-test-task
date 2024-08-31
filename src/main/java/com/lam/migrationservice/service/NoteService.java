package com.lam.migrationservice.service;

import com.lam.migrationservice.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesByGuids(List<String> guids);
    void saveAllNotes(List<Note> notesToSave);
}
