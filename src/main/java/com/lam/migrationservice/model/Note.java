package com.lam.migrationservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient_note")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModifiedDateTime;

    @Column(name = "note")
    private String  note;

    @Column(name = "guid")
    private String guid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_user_id")
    private User createdUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "last_modified_by_user_id")
    private User lastModifiedUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
