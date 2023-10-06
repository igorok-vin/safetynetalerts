package com.safetynet.safetynetalerts.serviceTest;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordsService;
import com.safetynet.safetynetalerts.service.MedicalRecordsServiceImplementation;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PersonServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Person service Test")
public class PersonServiceTest {
    private PersonRepository personRepository;
    private PersonService personService;
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private MedicalRecordRepository medicalRecordRepository;
    private MedicalRecordsService medicalRecordsService;
    private MedicalRecord medicalRecord1;
    private MedicalRecord medicalRecord2;
    private MedicalRecord medicalRecord3;
    private MedicalRecord medicalRecord4;

    @BeforeEach
    public void setup() {
        person1 = new Person("John", "Walker", "100 Melrose place", "Boston", "1234",
                "111-222", "Jonh@Walker");
        person2 = new Person("Tom", "Bond", "876 First street", "New York", "5679",
                "333-456", "Tom@Walker");
        person3 = new Person("Sarah", "Bond", "876 First street", "New York", "5679",
                "597-416", "Sarah@Conor");
        person4 = new Person("Anna", "Walker", "100 Melrose place", "Boston", "1234",
                "491-375", "Sarah@Conor");

        personRepository = new PersonRepository();
        medicalRecordRepository = new MedicalRecordRepository();
        medicalRecordsService = new MedicalRecordsServiceImplementation(medicalRecordRepository);
        personService = new PersonServiceImplementation(personRepository, medicalRecordsService);
        personService.addNewPerson(person1);
        personService.addNewPerson(person2);
        personService.addNewPerson(person3);
        personService.addNewPerson(person4);

        List<String> someMedication1 = new ArrayList<>();
        someMedication1.add("medication1");
        List<String> someMedication2 = new ArrayList<>();
        someMedication1.add("medication2");
        List<String> someMedication3 = new ArrayList<>();
        List<String> someMedication4 = new ArrayList<>();
        someMedication1.add("medication4");

        List<String>someAllergies1 = new ArrayList<>();
        someAllergies1.add("allergies1");
        List<String>someAllergies2 = new ArrayList<>();
        someAllergies2.add("allergies2");
        List<String>someAllergies3 = new ArrayList<>();
        List<String>someAllergies4 = new ArrayList<>();
        someAllergies4.add("allergies4");

       medicalRecord1 = new MedicalRecord ("John", "Walker", "05/07/1981",
               someMedication1, someAllergies1);
        medicalRecord2 = new MedicalRecord ("Tom", "Bond", "05/07/2015",
                someMedication2, someAllergies2);
        medicalRecord3 = new MedicalRecord ("Sarah", "Bond", "05/07/1979",
                someMedication3, someAllergies3);
        medicalRecord4 = new MedicalRecord ("Anna", "Walker", "05/07/1982",
                someMedication4, someAllergies4);

        medicalRecordsService.addNewMedicalRecord(medicalRecord1);
        medicalRecordsService.addNewMedicalRecord(medicalRecord2);
        medicalRecordsService.addNewMedicalRecord(medicalRecord3);
        medicalRecordsService.addNewMedicalRecord(medicalRecord4);
    }

    @Test
    @DisplayName("Find person by first name and last name Test")
    public void findPersonByFirstAndLastName() {
        //act
        Person findPerson = personService.findPersonByFirstAndLastName("Sarah","Bond");

        //assert
        assertTrue(findPerson.equals(person3));
    }

    @Test
    @DisplayName("Find all persons Test")
    public void findAllPersonsTest() {
        //act
        List<Person> personList = personService.findAllPersons();

       //assert
        assertThat(personList.toString(),containsString("Bond"));
        assertThat(personList.toString(),containsString("597-416"));
        assertThat(personList.toString(),containsString("Jonh@Walker"));
        assertThat(personList.toString(),containsString("100 Melrose place"));
        assertTrue((personList.get(3).getFirstName().equals("Anna")));
        assertEquals(personList.size(), 4);
    }

    @Test
    @DisplayName("Update person Test")
    public void updatePersonTest() {
        //arrange
        Person updatedPerson = new Person("Tom", "Bond", "555 King street", "Paris", "9586",
                "555-000", "jfg@");
        //act
        personService.updatePerson(updatedPerson);

        //assert
        assertThat(updatedPerson.toString(), containsString("555-000"));
        assertThat(updatedPerson.toString(), containsString("Paris"));
        assertEquals("9586", person2.getZip());
    }
    @Test
    @DisplayName("Delete person Test")
    public void deletePersonTest() {
        //act
        personService.deletePerson("Anna","Walker");
        Optional<Person> person = Optional.ofNullable(personService.findPersonByFirstAndLastName("Anna","Wallker"));

        //assert
        assertTrue(person.isEmpty());
    }

    @Test
    @DisplayName("Person age calculator Test")
    public void personAgeCalculatorTest() {
        //act
        String age = personService.personAgeCalculator("John","Walker");

        //assert
        assertEquals("42", age);
    }

    @Test
    @DisplayName("Information about the person when the person exist Test")
    public void personInfo_existingPersonTest() {
        //act
        Person person = personService.personInfo("Sarah","Bond");
        Optional<Person> optionalPerson = Optional.ofNullable(person);

        //assert
        assertTrue(optionalPerson.isPresent());
    }

    @Test
    @DisplayName("Information about the person when the person does not exist Test")
    public void personInfo_notExistingPerson() {
        //act
        Person person = personService.personInfo("James","Bond");
        Optional<Person> optionalPerson = Optional.ofNullable(person);

        //assert
        assertTrue(optionalPerson.isEmpty());

    }

    @Test
    @DisplayName("Find all person by address Test")
    public void findAllPersonByAddressTest() {
        //act
        List<Person> personsAtAddress = personService.findAllPersonByAddress("100 Melrose place");

        //assert
        assertThat(personsAtAddress.toString(), containsString("John"));
        assertTrue(personsAtAddress.get(1).getFirstName().equals("Anna"));
        assertEquals(personsAtAddress.size(),2);
    }

    @Test
    @DisplayName("Lists of children and adults at the given address Test")
    public void listsOfChildrenAndAdultsAtAddressTest() {
        //act
        List<Person> personsAtAddress = personService.listsOfChildrenAndAdultsAtAddress("876 First street");

        //assert
        assertEquals("Sarah", personsAtAddress.get(0).getFirstName());
        assertThat(personsAtAddress.toString(), containsString("Tom"));
        assertEquals("Tom", personsAtAddress.get(1).getFirstName());
        assertEquals(personsAtAddress.size(),2);
        assertTrue(personsAtAddress.get(1).getAge().equals("8"));
    }

    @Test
    @DisplayName("Number of adults in service area Test")
    public void numberOfAdultsInServiceAreaTest() {
        //arrange
        List<Person> allPersons = new ArrayList<>();
        allPersons.add(person1);
        allPersons.add(person2);
        allPersons.add(person3);
        allPersons.add(person4);

        //act
        String numberOfAdults = personService.numberOfAdultsInServiceArea(allPersons);

        //assert
        assertThat(numberOfAdults, containsString("3"));
    }

    @Test
    @DisplayName("Providing all email addresses by the city name Test")
    public void emailAddressesByCityTest() {
       //arrange
        String city = "Boston";

        //act
        List<String> emailsList = personService.emailAddressesByCity(city);

        //assert
        assertThat(emailsList.toString(),containsString("Jonh@Walker"));
        assertEquals("Jonh@Walker", emailsList.get(0));
        assertTrue(emailsList.get(1).equals("Sarah@Conor"));
    }

    @Test
    @DisplayName("Find all persons by corresponding addresses Test")
    public void findAllPersonsByCorrespondingAddressesTest() {
        //arrange
        List<String> adrresess = new ArrayList<>();
        adrresess.add("100 Melrose place");
        adrresess.add("876 First street");

        //act
        List<Person> allPersons = personService.findAllPersonsByCorrespondingAddresses(adrresess);

        //assert
        assertEquals("John", allPersons.get(0).getFirstName());
        assertThat(allPersons.toString(),containsString("Anna"));
        assertTrue(allPersons.get(2).getFirstName().equals("Tom"));

    }

    @Test
    @DisplayName("Find all children only in serviced area Test")
    public void findChildrenOnlyInServicedAreaTest() {
        //arrange
        List<Person> allPersons = new ArrayList<>();
        allPersons.add(person1);
        allPersons.add(person2);
        allPersons.add(person3);
        allPersons.add(person4);

        //act
        String childrenOnly = personService.findChildrenOnlyInServicedArea(allPersons);

        //assert
        assertEquals(childrenOnly,"1");
    }
}
