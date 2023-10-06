package com.safetynet.safetynetalerts.serviceTest;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordsService;
import com.safetynet.safetynetalerts.service.MedicalRecordsServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import org.junit.Before;
//import org.junit.Test;

//@RunWith(SpringRunner.class)
@DisplayName("Medical record service Test ")
public class MedicalRecordServiceTest {

    private MedicalRecord medicalRecord1;
    private MedicalRecord medicalRecord2;
    private MedicalRecordsService medicalRecordsService;
    private MedicalRecordRepository medicalRecordRepository;


    @BeforeEach
    public void setup(){
       medicalRecordRepository = new MedicalRecordRepository();
       medicalRecordsService = new MedicalRecordsServiceImplementation(medicalRecordRepository);

       List<String> medications1 = new ArrayList<>(); medications1.add("medication1");
       List<String> medications2 = new ArrayList<>(); medications2.add("medication2");

       List<String> allergies1 = new ArrayList<>(); allergies1.add("allergies1");
       List<String> allergies2 = new ArrayList<>(); allergies1.add("allergies2");

       medicalRecord1 = new MedicalRecord("Sarah","Conor","02/03/1965",medications1,allergies1);
       medicalRecord2 = new MedicalRecord("John","Walker","05/05/1960",medications2,allergies2);

       medicalRecordsService.addNewMedicalRecord(medicalRecord1);
       medicalRecordsService.addNewMedicalRecord(medicalRecord2);
    }

    @Test
    @DisplayName("Find all medical records Test")
    public void findAllMedicalRecordsTest() {
        //act
        List<MedicalRecord> medicalRecords = medicalRecordsService.findAllMedicalRecords();

        //assert
        assertThat(medicalRecords.toString(),containsString("Sarah"));
        assertThat(medicalRecords.toString(),containsString("John"));
        assertTrue(medicalRecords.size()==2);
        assertTrue(medicalRecords.get(0).getBirthdate().equals("02/03/1965"));
    }

    @Test
    @DisplayName("Add new medical record Test")
    public void addNewMedicalRecordTest() {
      //arrange
      List<String> medications = new ArrayList<>();medications.add("medication");
      List<String> allergies = new ArrayList<>();allergies.add("allergies");

      //act
        MedicalRecord medicalRecord = new MedicalRecord("Brad","Pitt","08/08/1960",medications,allergies);

      //assert
        assertThat(medicalRecord.toString(),containsString("Brad"));
        assertThat(medicalRecord.toString(),containsString("Pitt"));
        assertTrue(medicalRecord.getBirthdate().equals("08/08/1960"));
    }

    @Test
    @DisplayName("Update medical rercord Test")
    public void updateMedicalRercordTest() {
        //arrange
        List<String> medications = new ArrayList<>();medications.add("medication");
        List<String> allergies = new ArrayList<>();allergies.add("allergies");
        MedicalRecord medicalRecord = new MedicalRecord("Sarah","Conor","08/08/1960",medications,allergies);

        //act
        medicalRecordRepository.updateMedicalRercord(medicalRecord);

        //assert
        assertThat(medicalRecord.toString(),containsString("08/08/1960"));
    }

    @Test
    @DisplayName("Delete medical record Test")
    public void deleteMedicalRecordTest() {
        //act
        medicalRecordRepository.deleteMedicalRecord("John", "Walker");
        Optional<MedicalRecord> medicalRecord = Optional.ofNullable(medicalRecordRepository
                .findMedicalRecordsByFirstAndLastName("John","Walker"));
        //assert
        assertTrue(medicalRecord.isEmpty());
    }
}

