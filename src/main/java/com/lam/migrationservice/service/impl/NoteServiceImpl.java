package com.lam.migrationservice.service.impl;

import com.lam.migrationservice.model.Note;
import com.lam.migrationservice.repository.NoteRepository;
import com.lam.migrationservice.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class NoteServiceImpl implements NoteService {
    @Value("${migration.batchSize}")
    private Integer batchSize;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getNotesByGuids(List<String> guids) {
        return noteRepository.findAllByGuids(guids);
    }

    @Override
    public void saveAllNotes(List<Note> notesToSave) {
        int totalNotes = notesToSave.size();
        for (int i = 0; i < totalNotes; i += batchSize) {
            int endIndex = Math.min(i + batchSize, totalNotes);
            List<Note> batch = notesToSave.subList(i, endIndex);
            noteRepository.saveAllAndFlush(batch);
            log.info("Saved {} notes", batch.size());
        }
    }
}
