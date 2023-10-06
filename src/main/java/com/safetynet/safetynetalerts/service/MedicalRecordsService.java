package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordsService {

  public   MedicalRecord findMedicalRecordsByFirstAndLastName(String firstName, String lastName);

    public  List<MedicalRecord> findAllMedicalRecords();

    public  MedicalRecord addNewMedicalRecord(MedicalRecord record);

    public void updateMedicalRercord(MedicalRecord record);

    public void deleteMedicalRecord(String firstName, String lastName);
}
