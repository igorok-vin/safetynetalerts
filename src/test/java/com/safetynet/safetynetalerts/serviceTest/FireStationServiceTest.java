package com.safetynet.safetynetalerts.serviceTest;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonsListServicedByStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


@DisplayName("Firestation service Test ")
public class FireStationServiceTest {
    private static PersonRepository personRepository;
    private static PersonService personService;
    private static MedicalRecordRepository medicalRecordRepository;
    private static MedicalRecordsService medicalRecordsService;
    private static FireStationRepository fireStationRepository;
    private static FireStationService fireStationService;
    private static FireStation fireStation1;
    private static FireStation fireStation2;
    private static Person person1;
    private static Person person2;
    private static MedicalRecord medicalRecord1;
    private static MedicalRecord medicalRecord2;

    @BeforeEach
    public void initialize() {
        medicalRecordRepository = new MedicalRecordRepository();
        medicalRecordsService = new MedicalRecordsServiceImplementation(medicalRecordRepository);

        List<String> someMedication1 = new ArrayList<>();
        someMedication1.add("medication1");
        List<String> someMedication2 = new ArrayList<>();
        someMedication2.add("medication2");

        List<String> someAllergies1 = new ArrayList<>();
        someAllergies1.add("allergies1");
        List<String> someAllergies2 = new ArrayList<>();
        someAllergies2.add("allergies2");

        medicalRecord1 = new MedicalRecord("Tom", "Hanks", "07/07/1955",
                someMedication1, someAllergies1);
        medicalRecord2 = new MedicalRecord("Sarah", "Bond", "05/05/1960",
                someMedication2, someAllergies2);

        medicalRecordsService.addNewMedicalRecord(medicalRecord1);
        medicalRecordsService.addNewMedicalRecord(medicalRecord2);

        //////////
        personRepository = new PersonRepository();
        personService = new PersonServiceImplementation(personRepository, medicalRecordsService);

        person1 = new Person("Tom", "Hanks", "456 King Street", "New York", "5679",
                "333-456", "Tom@Walker");
        personService.addNewPerson(person1);

        person2 = new Person("Sarah", "Bond", "789 Queen Street", "Boston", "6974",
                "597-416", "Sarah@Conor");
        personService.addNewPerson(person2);

        //////////
        fireStationRepository = new FireStationRepository();
        fireStationService = new FireStationServiceImplementation
                (fireStationRepository, medicalRecordsService, personService);

        fireStation1 = new FireStation("456 King Street", "1");
        fireStationService.addNewFireStation(fireStation1);

        fireStation2 = new FireStation("789 Queen Street", "2");
        fireStationService.addNewFireStation(fireStation2);
    }

    @Test
    @DisplayName("Find fireStation by address Test")
    public void findFireStationByAddressTest() {
        //act
        List<FireStation> fireStations = fireStationService.findAllFireStations();
        assertThat(fireStations.toString(),containsString("1"));
        assertThat(fireStations.toString(),containsString("1"));
        assertTrue(fireStations.size()==2);
    }

    @Test
    @DisplayName("Add new firestation Test")
    public void addNewFireStationTest() {
        //arrange
        FireStation fireStation = new FireStation("634 East street","3");

        //act
       FireStation station = fireStationService.addNewFireStation(fireStation);

       //assert
        assertThat(station.toString(),containsString("3"));

    }

    @Test
    @DisplayName("Update existing firestation Test")
    void updateFireStationTest() {
        //arrange
        FireStation updatedStation = fireStationRepository.findStationByAddress("456 King Street");
        updatedStation.setAddress("579 King Street");

        //act
        fireStationService.updateFireStation(updatedStation);

        //assert
        assertEquals(fireStation1.getAddress(),"579 King Street");
    }

    @Test
    @DisplayName("Delete firestation Test")
    public void deleteFireStationTest() {
        //act
        fireStationService.deleteFireStation("456 King Street");
        List<FireStation> stationList = fireStationService.findAllFireStations();

        //assert
        assertTrue(stationList.size()==1);
        assertTrue(fireStationService.findFireStationByAddress("456 King Street")==null);
    }

    @Test
    @DisplayName("Find all addresses serviced by firestation Test")
    public void findAllAddressesServicedByFireStationTest() {
        //arrange
        FireStation fireStation3 = new FireStation("Marriot","2");
        fireStationService.addNewFireStation(fireStation3);
        FireStation fireStation4 = new FireStation("First street","2");
        fireStationService.addNewFireStation(fireStation4);

        //act
        List<String> fireStationsList = fireStationService.findAllAddressesServicedByFireStation("2");

        //assert
        assertTrue(fireStationsList.size()==3);
        assertThat(fireStationsList.toString(),containsString("Marriot"));
        assertTrue(!fireStationsList.contains("456 King Street"));
    }

    @Test
    @DisplayName("Find all persons serviced by firestation Test")
    public void findAllPersonsServicedByFireStationTest() {
        //act
        List<Person> personList = fireStationService.findAllPersonsServicedByFireStation("1");

        //assert
        assertThat(personList.toString(),containsString("Tom"));
        assertThat(personList.toString(),containsString("Hanks"));
    }

    @Test
    @DisplayName("Find phone numbers each person within firestations jurisdiction Test")
    public void phoneNumbersEachPersonWithinFireStationsJurisdictionTest() {
        //act
        List<String> phoneNumbersList = fireStationService
                .phoneNumbersEachPersonWithinFireStationsJurisdiction("2");

        //assert
        assertThat(phoneNumbersList.toString(),containsString("597-416"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Retrn list of adults and children serviced by corresponding firestation Test")
    public void listAdultsAndchildrenServicedByCorrespondingFireStationTest() {
        //act
        PersonsListServicedByStation personsList = fireStationService
                .listAdultsAndchildrenServicedByCorrespondingFireStation("1");

        //assert
        assertEquals(personsList.getNumberOfAdults(),"1");
        assertEquals(personsList.getNumberOfChildren(),"0");
    }




}
