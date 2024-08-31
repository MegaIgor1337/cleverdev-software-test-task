package com.lam.migrationservice.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PatientStatus {
    ACTIVE(210),
    INACTIVE(220),
    DELETED(230);
    private static final Map<Integer, PatientStatus> BY_ID = new HashMap<>();

    static {
        for (PatientStatus e : values()) {
            BY_ID.put(e.statusId, e);
        }
    }
    private final int statusId;

    PatientStatus(int statusId) {
        this.statusId = statusId;
    }

    public static PatientStatus valueOfId(int id) {
        return BY_ID.get(id);
    }

}
