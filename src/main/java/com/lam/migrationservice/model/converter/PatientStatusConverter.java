package com.lam.migrationservice.model.converter;

import com.lam.migrationservice.model.PatientStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class PatientStatusConverter implements AttributeConverter<PatientStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PatientStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getStatusId();

    }

    @Override
    public PatientStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return PatientStatus.valueOfId(dbData);
    }
}
