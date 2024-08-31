package com.lam.migrationservice.repository;

import com.lam.migrationservice.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note AS n " +
            "WHERE n.guid in (:guids) ")
    List<Note> findAllByGuids(@Param("guids") List<String> guids);
}
