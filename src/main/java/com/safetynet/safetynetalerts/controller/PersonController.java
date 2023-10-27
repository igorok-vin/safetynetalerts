package com.safetynet.safetynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.JSONReader;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {

    private final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;
    private JSONReader jsonReader;

    @Autowired
    public PersonController(PersonService personService, JSONReader jsonReader) {
        this.personService = personService;
        this.jsonReader = jsonReader;
    }

    @GetMapping("/persons")
    public String findAllPersons() {
        logger.info("HTTP GET request received for /persons URL");
        Optional<Boolean> persons = Optional.ofNullable(personService.findAllPersons().isEmpty());
        if (persons.get()) {
            logger.error("ERROR During HTTP GET request. The list of persons has been not created");
            return String.format("ERROR During HTTP GET request. The list of persons has been not created");
    }else {
            logger.info("Success. The list of persons is created");
            return JsonStream.serialize(personService.findAllPersons());
        }
    }

    @PostMapping("/person")
    public String addNewPerson(@RequestBody Person person) {
        logger.info("HTTP POST request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(person.getFirstName(),person.getFirstName()));
        System.out.println(personServiceOptional);
        if(personServiceOptional.isEmpty()){
            logger.info("Success. The person is created");
            return JsonStream.serialize(personService.addNewPerson(person));
        } else {
            logger.error("ERROR During HTTP POST request. The person alredy exist");
            return String.format("ERROR During HTTP POST request. The person alredy exist");
        }
    }

    @PutMapping("/person")
    public void updatePerson(@RequestBody Person person, @RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.info("HTTP PUT request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(firstName,lastName));
        System.out.println(personServiceOptional);
        if(personServiceOptional.isPresent()){
            logger.info("Success. The person is updated");
            personService.updatePerson(person);
        } else {
            logger.error("ERROR During HTTP PUT request.The person not updated");
        }
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.info("HTTP DELETE request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(firstName,lastName));
        if(personServiceOptional.isPresent()){
            logger.info("Success. The person is deleted");
            personService.deletePerson(firstName,lastName);
        } else {
            logger.error("ERROR During HTTP DELETE request.The person not deleted");
        }
    }

    @GetMapping("/childAlert")
    public String listsOfChildrenAndAdultsAtAddress(@RequestParam String address){
        logger.info("HTTP GET request received for /childAlert URL");
        Optional<Boolean> personServiceOptional = Optional.ofNullable(personService
                .listsOfChildrenAndAdultsAtAddress(address).isEmpty());
        if(personServiceOptional.get()){
            logger.error("ERROR During HTTP GET request." +
                    "The list of children and other persons living at provided address not created");
            return String.format("ERROR During HTTP GET request." +
                    "The list of children and other persons living at provided address not created");
        } else {
            logger.info("Success.The list of children and other persons living at provided address was created.");
            return JsonStream.serialize(personService
                    .listsOfChildrenAndAdultsAtAddress(address));
        }
    }

    @GetMapping("/personInfo")
    public String personInfo(@RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.info("HTTP GET request received for /childAlert URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(firstName,lastName));
        if(personServiceOptional.isEmpty()){
            logger.error("ERROR During HTTP GET request." +
                    "The personal information for the provided person was not found.");
            return String.format("ERROR During HTTP GET request." +
                    "The personal information for the provided person was not found.");
        } else {
            logger.info("Success.The personal information for the provided person was found.");
            return JsonStream.serialize(personService.personInfo(firstName,lastName));
        }
    }

    @GetMapping("/communityEmail")
    public String emailAddressesByCity(@RequestParam String city){
        logger.info("HTTP GET request received for /communityEmail URL");
        Optional <Boolean> personServiceOptional = Optional.ofNullable(personService
                .emailAddressesByCity(city).isEmpty());
        if(personServiceOptional.get()){
            logger.error("ERROR During HTTP GET request." +
                    "The list of all emails in the provided city was not created.");
            return String.format("ERROR During HTTP GET request." +
                    "The list of all emails in the provided city was not created.");
        } else {
            logger.info("Success.The list of all emails in the provided city was created.");
            return JsonStream.serialize(personService.emailAddressesByCity(city));
        }
    }
}
