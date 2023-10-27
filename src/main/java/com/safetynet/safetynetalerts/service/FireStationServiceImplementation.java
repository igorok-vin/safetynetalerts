package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Household;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonsListServicedByStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationServiceImplementation implements FireStationService {
    private FireStationRepository fireStationRepository;
    private MedicalRecordsService medicalRecordsService;
    private PersonService personService;

    @Autowired
    public FireStationServiceImplementation(FireStationRepository fireStationRepository,
                                            MedicalRecordsService medicalRecordsService,
                                            PersonService personService) {
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordsService = medicalRecordsService;
        this.personService = personService;
    }

    @Override
    public FireStation findFireStationByAddress(String address) {
        return fireStationRepository.findStationByAddress(address);
    }

    @Override
    public List<FireStation> findAllFireStations() {
        return fireStationRepository.findAllStations();
    }

    @Override
    public FireStation addNewFireStation(FireStation fireStation) {
        List<FireStation> existingFireStation = fireStationRepository.findAllStations();
        for (FireStation fireStationInList : existingFireStation) {
            if (fireStationInList.equals(fireStation)) {
                return null;
            }
        }
        return fireStationRepository.addNewFireStation(fireStation);
    }

    @Override
    public void updateFireStation(FireStation fireStation, String address) {
        FireStation updateFireStation = fireStationRepository.findStationByAddress(address);
        if (updateFireStation != null) {
            fireStationRepository.updateFireStation(fireStation, address);
        }
    }

    @Override
    public void deleteFireStation(String address) {
        FireStation deleteFireStation = fireStationRepository.findStationByAddress(address);
        if (deleteFireStation != null) {
            fireStationRepository.deleteFireStation(address);
        }
    }

    @Override
    public List<String> findAllAddressesServicedByFireStation(String stationNumber) {
        List<FireStation> fireStationList = fireStationRepository.findAllStations()
                .stream().filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .collect(Collectors.toList());
        List<String> addresses = new ArrayList<>();
        fireStationList.forEach(fireStation -> addresses.add(fireStation.getAddress()));
        return addresses;
    }

    @Override
    public List<Person> findAllPersonsServicedByFireStation(String stationNumber) {
        List<Person> people = new ArrayList<>();
        List<String> addresses = findAllAddressesServicedByFireStation(stationNumber);
        if (fireStationRepository.findAllStations()
                .stream()
                .anyMatch(fireStation -> fireStation.getStation().equals(stationNumber))) {
            people = personService.findAllPersonsByCorrespondingAddresses(addresses);
        }
        ;
        return people;
    }

    @Override
    public List<String> phoneNumbersEachPersonWithinFireStationsJurisdiction(String stationNumber) {
        List<String> phoneNumbersList = new ArrayList<>();
        if ( fireStationRepository.findAllStations()
                .stream()
                .anyMatch(fireStation -> fireStation.getStation().equals(stationNumber))) {
            List<Person> people = findAllPersonsServicedByFireStation(stationNumber);
            people.forEach(person -> {
                phoneNumbersList.add(person.getPhone());
            });
        }
        return phoneNumbersList;
    }

    @Override
    public PersonsListServicedByStation listAdultsAndchildrenServicedByCorrespondingFireStation(String stationNumber) {
        List<Person> personsList = findAllPersonsServicedByFireStation(stationNumber);
        List<String> newPersonList = new ArrayList<>();
        for (Person person : personsList) {
            newPersonList.add(person.getFirstName());
            newPersonList.add(person.getLastName());
            newPersonList.add(person.getAddress());
            newPersonList.add(person.getPhone());
        }
        PersonsListServicedByStation listServicedByStation = new PersonsListServicedByStation(stationNumber,
                personService.numberOfAdultsInServiceArea(personsList),
                personService.findChildrenOnlyInServicedArea(personsList),newPersonList);
        return listServicedByStation;
    }

    @Override
    public List<Household> findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(List<String> stationsList){
        List<Household> households = new ArrayList<>();

        for(String station: stationsList){
            if(fireStationRepository.findAllStations()
                    .stream()
                    .anyMatch(fireStation -> fireStation.getStation().equals(station))){
                        Household household;
                        List<String> householdAddresses = findAllAddressesServicedByFireStation(station);

                        for(String address: householdAddresses){
                            List<Person> givenPersonList = personService.findAllPersonByAddress(address);
                            List<Person> newPersonList = new ArrayList<>();

                            givenPersonList.forEach(person -> {
                                Person personNew = new Person(person.getFirstName(), person.getLastName(), person.getPhone(),
                                        personService.personAgeCalculator(person.getFirstName(), person.getLastName()),
                                        medicalRecordsService.findMedicalRecordsByFirstAndLastName(person.getFirstName(), person.getLastName()).getMedications(),
                                        medicalRecordsService.findMedicalRecordsByFirstAndLastName(person.getFirstName(), person.getLastName()).getAllergies());
                                newPersonList.add(personNew);
                            });
                            household = new Household(address,newPersonList);
                            households.add(household);
                }
            }
        }
        return households;
    }

    @Override
    public Map<String, List<Person>> findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(String address) {
        Map<String, List<Person>> listHashMap = new HashMap<>();
        List<Person> newPersonList = new ArrayList<>();
        if (fireStationRepository.findAllStations()
                .stream()
                .anyMatch(fireStation -> fireStation.getAddress().equals(address))) {
            FireStation station = fireStationRepository.findStationByAddress(address);
            List<Person> people = findAllPersonsServicedByFireStation(station.getStation());
            people.forEach(person -> {
                Person personNew = new Person(person.getFirstName(), person.getLastName(), person.getPhone(),
                        personService.personAgeCalculator(person.getFirstName(), person.getLastName()),
                        medicalRecordsService.findMedicalRecordsByFirstAndLastName(person.getFirstName(), person.getLastName()).getMedications(),
                        medicalRecordsService.findMedicalRecordsByFirstAndLastName(person.getFirstName(), person.getLastName()).getAllergies());
                newPersonList.add(personNew);
            });
            listHashMap.put("station number: "+ station.getStation(), newPersonList);
        }
        return listHashMap;
    }
}
