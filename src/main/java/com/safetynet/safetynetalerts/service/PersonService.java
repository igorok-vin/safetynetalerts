package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface PersonService {

    public Person findPersonByFirstAndLastName(String firstName, String lastName);

    public List<Person> findAllPersons();

    public Person addNewPerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(String firstName, String lastName);

    public String personAgeCalculator(String firstName, String lastName);

    public Person personInfo(String firstName, String lastName);

    public List<Person> findAllPersonByAddress(String address);

    public List<Person> listsOfChildrenAndAdultsAtAddress(String address);

    public String numberOfAdultsInServiceArea(List<Person> adults);

    public List<String> emailAddressesByCity(String city);

    public List<Person> findAllPersonsByCorrespondingAddresses(List<String> addresses);

    public String findChildrenOnlyInServicedArea(List<Person> children);

}
