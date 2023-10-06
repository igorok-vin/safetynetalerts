package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImplementation implements MedicalRecordsService {

    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordsServiceImplementation(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord findMedicalRecordsByFirstAndLastName(String firstName, String lastName) {
        return medicalRecordRepository.findMedicalRecordsByFirstAndLastName(firstName, lastName);
    }

    @Override
    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecordRepository.findAllMedicalRecords();
    }

    @Override
    public MedicalRecord addNewMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> exestingRecord = medicalRecordRepository.findAllMedicalRecords();
        for (MedicalRecord recordInList : exestingRecord) {
            if (recordInList.equals(record)) {
                return null;
            }
        }
        return medicalRecordRepository.addNewMedicalRecord(record);
    }

    @Override
    public void updateMedicalRercord(MedicalRecord record) {
        MedicalRecord exestingRecord = medicalRecordRepository.findMedicalRecordsByFirstAndLastName(record.getFirstName(), record.getLastName());
        if (exestingRecord != null) {
            medicalRecordRepository.updateMedicalRercord(record);
        }
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        if (medicalRecordRepository.findMedicalRecordsByFirstAndLastName(firstName, lastName) != null) {
            medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
        }
    }


}
