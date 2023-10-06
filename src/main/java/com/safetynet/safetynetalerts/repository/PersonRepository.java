package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

     List<Person> personList  = new ArrayList<>();

    public Person findPerson(String firstName, String lastName) {
        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    public List<Person> findAllPersons() {
        return personList;
    }

    public Person addNewPerson(Person person) {
        personList.add(person);
        return person;
    }

    public void updatePerson(Person person){
        Person updatePerson = findPerson(person.getFirstName(),person.getLastName());
        updatePerson.setAddress(person.getAddress());
        updatePerson.setCity(person.getCity());
        updatePerson.setZip(person.getZip());
        updatePerson.setPhone(person.getPhone());
        updatePerson.setEmail(person.getEmail());
    }

        public void deletePerson(String firstName, String lastName){
        Person deletePerson = findPerson(firstName,lastName);
        personList.remove(deletePerson);
    }
}
