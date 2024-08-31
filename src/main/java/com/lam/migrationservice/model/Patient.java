package com.lam.migrationservice.model;

import com.lam.migrationservice.model.converter.PatientStatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient_profile")
public class Patient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "old_client_guid")
    private String oldClientGuid;

    @Convert(converter = PatientStatusConverter.class)
    @Column(name = "status_id")
    private PatientStatus status;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();
}
