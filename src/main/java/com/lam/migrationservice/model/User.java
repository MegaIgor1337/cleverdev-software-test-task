package com.lam.migrationservice.model;

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
@Table(name = "company_user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @OneToMany(mappedBy = "createdUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> createdNotes = new ArrayList<>();

    @OneToMany(mappedBy = "lastModifiedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> modifiedNotes = new ArrayList<>();
}
