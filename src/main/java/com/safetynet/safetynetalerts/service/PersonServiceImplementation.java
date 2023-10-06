package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImplementation implements PersonService {

    private PersonRepository personRepository;
    private MedicalRecordsService medicalRecordsService;

    @Autowired
    public PersonServiceImplementation(PersonRepository personRepository,
                                       MedicalRecordsService medicalRecordsService) {
        this.personRepository = personRepository;
        this.medicalRecordsService = medicalRecordsService;
    }

    @Override
    public Person findPersonByFirstAndLastName(String firstName, String lastName) {

        return personRepository.findPerson(firstName, lastName);
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAllPersons();
    }

    @Override
    public Person addNewPerson(Person person) {
        List<Person> existingPerson = personRepository.findAllPersons();
        for (Person personInList : existingPerson) {
            if (personInList.equals(person)) {
                return null;
            }
        }
        return personRepository.addNewPerson(person);
    }

    @Override
    public void updatePerson(Person person) {
        Person updatePerson = personRepository.findPerson(person.getFirstName(), person.getLastName());
        if (updatePerson != null) {
            personRepository.updatePerson(person);
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        Person deletePerson = personRepository.findPerson(firstName, lastName);
        if (deletePerson != null) {
            personRepository.deletePerson(firstName, lastName);
        }
    }

    @Override
    public String personAgeCalculator(String firstName, String lastName) {
        String personAge = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = LocalDate.now();
        MedicalRecord medicalRecord;

        if (medicalRecordsService.findMedicalRecordsByFirstAndLastName(firstName, lastName)!=null) {
            medicalRecord = medicalRecordsService.findMedicalRecordsByFirstAndLastName(firstName, lastName);
            LocalDate personBirthdate = LocalDate.parse(medicalRecord.getBirthdate(), dateFormat);
            personAge = Integer.toString(Period.between(personBirthdate, currentDate).getYears());
        }
        return personAge;
    }

    @Override
    public Person personInfo(String firstName, String lastName) {
        Optional<Person> personInfo = Optional.ofNullable(personRepository.findPerson(firstName, lastName));
        if (personInfo.isPresent()) {
            Person newPerson = new Person(firstName, lastName,
                    personRepository.findPerson(firstName, lastName).getAddress(),
                    personAgeCalculator(firstName, lastName),
                    personRepository.findPerson(firstName, lastName).getEmail(),
                    medicalRecordsService.findMedicalRecordsByFirstAndLastName(firstName, lastName).getMedications(),
                    medicalRecordsService.findMedicalRecordsByFirstAndLastName(firstName, lastName).getAllergies());
            return newPerson;
        }
        return null;
    }

    /**
     * Get list of person at address
     *
     * @param address specific address
     * @return list of person lives in the same address
     */
    @Override
    public List<Person> findAllPersonByAddress(String address) {
        List<Person> getPersonByAddress = new ArrayList<>();
            if (personRepository.findAllPersons().stream().anyMatch(p -> p.getAddress().equals(address))) {
                getPersonByAddress= personRepository.findAllPersons()
                        .stream().filter(p -> p.getAddress().equals(address))
                        .collect(Collectors.toList());
            }
        return getPersonByAddress;
    }

    @Override
    public List<Person> listsOfChildrenAndAdultsAtAddress(String address) {
        List<Person> personsList = new ArrayList<>();
        List<Person> children = new ArrayList<>();
        List<Person> adult = new ArrayList<>();
        List<Person> householdMembers = new ArrayList<>();
        if (personRepository.findAllPersons().stream().anyMatch(person -> person.getAddress().equals(address))) {
            personsList = findAllPersonByAddress(address);
            for (Person person : personsList) {
                Person personNew = new Person(person.getFirstName(),
                                              person.getLastName(),
                                              personAgeCalculator(person.getFirstName(),person.getLastName()));
                int personAge = Integer.parseInt(personAgeCalculator(person.getFirstName(), person.getLastName()));
                if (personAge < 18) {
                    children.add(personNew);
                } else {
                    adult.add(personNew);
                }
            }
        }
        if (children !=null) {
            householdMembers.addAll(adult);
            householdMembers.addAll(children);
            return householdMembers;

        } else {
            return children;
        }
    }

    @Override
    public String numberOfAdultsInServiceArea(List<Person> adults) {
        int numberOfAdults = 0;
        for (Person person : adults) {
            int age = Integer.parseInt(personAgeCalculator(person.getFirstName(), person.getLastName()));
            if (age >= 18) {
                numberOfAdults++;
            }
        }
        String number = Integer.toString(numberOfAdults);
        return number;
    }

    @Override
    public List<String> emailAddressesByCity(String city) {
        List<String> personEmails = new ArrayList<>();
        List<Person> personList = personRepository.findAllPersons();

        for (Person person : personList) {
            if (person.getCity().equals(city)) {
                personEmails.add(person.getEmail());
            }
        }
        return personEmails;
    }

    @Override
    public List<Person> findAllPersonsByCorrespondingAddresses(List<String> addresses) {
        List<Person> people = new ArrayList<>();
        for (String allAddresses : addresses) {
            people.addAll(personRepository.findAllPersons()
                    .stream()
                    .filter(person -> person.getAddress().equals(allAddresses))
                    .collect(Collectors.toList()));
        }
        return people;
    }

    @Override
    public String findChildrenOnlyInServicedArea(List<Person> children) {
        int childrenTotal = 0;
        for (Person person : children) {
            if (Integer.parseInt(personAgeCalculator(person.getFirstName(), person.getLastName())) < 18) {
                childrenTotal = childrenTotal + 1;
            }
        }
        return Integer.toString(childrenTotal);
    }

}
