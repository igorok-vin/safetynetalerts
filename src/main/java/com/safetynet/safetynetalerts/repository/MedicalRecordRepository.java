package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    List<MedicalRecord> medicalRecords = new ArrayList<>();

    public MedicalRecord findMedicalRecordsByFirstAndLastName(String firstName, String lastName) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getFirstName().equals(firstName) && record.getLastName().equals(lastName)) {
                return record;
            }
        }
        return null;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecords;
    }

    public MedicalRecord addNewMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
        return record;
    }

    public void updateMedicalRercord(MedicalRecord record) {
        MedicalRecord updateMedicalRecord = findMedicalRecordsByFirstAndLastName(record.getFirstName(),
                record.getLastName());
        updateMedicalRecord.setFirstName(record.getFirstName());
        updateMedicalRecord.setLastName(record.getLastName());
        updateMedicalRecord.setBirthdate(record.getBirthdate());
        updateMedicalRecord.setAllergies(record.getAllergies());
        updateMedicalRecord.setMedications(record.getMedications());
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        MedicalRecord deleteMedicalRecord = findMedicalRecordsByFirstAndLastName(firstName, lastName);
        medicalRecords.remove(deleteMedicalRecord);
    }
}
