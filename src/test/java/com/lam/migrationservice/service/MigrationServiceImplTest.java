package com.lam.migrationservice.service;


import com.lam.migrationservice.MigrationServiceApplicationTests;
import com.lam.migrationservice.repository.NoteRepository;
import com.lam.migrationservice.repository.UserRepository;
import com.lam.migrationservice.service.impl.MigrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;


import java.time.LocalDateTime;

import static com.lam.migrationservice.util.TestData.getNoteResponseDtoList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MigrationServiceApplicationTests.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_users.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_patients.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_patient_note.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
public class MigrationServiceImplTest {
    @Autowired
    private MigrationServiceImpl migrationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @MockBean
    private WebService webService;


    @Test
    void shouldSaveNewNotesAndUpdateOld() {
        when(webService.findNotes(anyList()))
                .thenReturn(getNoteResponseDtoList());

        migrationService.importNotes();

        assertEquals(5, userRepository.findAll().size());
        assertEquals(6, noteRepository.findAll().size());
        assertEquals("Changed comment", noteRepository.findById(1L).get().getNote());
        assertEquals("Cute client", noteRepository.findById(3L).get().getNote());
        assertEquals(LocalDateTime.of(2024, 12, 22, 12, 20, 47),
                noteRepository.findById(3L).get().getLastModifiedDateTime());

    }
}
