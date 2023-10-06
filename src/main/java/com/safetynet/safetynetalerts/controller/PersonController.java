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
        logger.debug("HTTP GET request received for /persons URL");
        Optional<JSONReader> jsonReader1 = Optional.empty();
        if (!jsonReader1.isPresent()) {
            logger.info("Success. The list of persons is created");
        return JsonStream.serialize(personService.findAllPersons());
    }else {
            logger.error("ERROR During HTTP GET request. The list of persons has been not created");
            return String.format("ERROR During HTTP GET request. The list of persons has been not created");
        }
    }

    @PostMapping("/person")
    public String addNewPerson(@RequestBody Person person) {
        logger.debug("HTTP POST request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(person.getFirstName(),person.getFirstName()));
        if(!personServiceOptional.isPresent()){
            logger.info("Success. The person is created");
            return JsonStream.serialize(personService.addNewPerson(person));
        } else {
            logger.error("ERROR During HTTP POST request. The person alredy exist");
            return String.format("ERROR During HTTP POST request. The person alredy exist");
        }

    }

    @PutMapping("/person")
    public void updatePerson(@RequestBody Person person) {
        logger.debug("HTTP PUT request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(person.getFirstName(),person.getFirstName()));
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
        logger.debug("HTTP DELETE request received for /person URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(firstName,lastName));
        if(personServiceOptional.isPresent()){
            logger.info("Success. The firestation deleted");
            personService.deletePerson(firstName,lastName);
        } else {
            logger.error("ERROR During HTTP DELETE request.The person not deleted");
        }
    }

    @GetMapping("/childAlert")
    public String listsOfChildrenAndAdultsAtAddress(@RequestParam String address){
        logger.debug("HTTP GET request received for /childAlert URL");
        boolean personServiceOptional = Optional.ofNullable(personService
                .listsOfChildrenAndAdultsAtAddress(address)).isPresent();
        if(personServiceOptional){
            logger.info("Success.The list of children and other persons living at provided address was created.");
            return JsonStream.serialize(personService
                    .listsOfChildrenAndAdultsAtAddress(address));
        } else {
                logger.error("ERROR During HTTP GET request." +
                        "The list of children and other persons living at provided address not created");
                return String.format("ERROR During HTTP GET request." +
                        "The list of children and other persons living at provided address not created");
        }
    }

    @GetMapping("/personInfo")
    public String personInfo(@RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.debug("HTTP GET request received for /childAlert URL");
        Optional<Person> personServiceOptional = Optional.ofNullable(personService
                .findPersonByFirstAndLastName(firstName,lastName));
        if(personServiceOptional.isPresent()){
            logger.info("Success.The personal information for the provided person was found.");
            return JsonStream.serialize(personService.personInfo(firstName,lastName));
        } else {
            logger.info("ERROR During HTTP GET request." +
                    "The personal information for the provided person was not found.");
           return String.format("ERROR During HTTP GET request." +
                    "The personal information for the provided person was not found.");
        }
    }

    @GetMapping("/communityEmail")
    public String emailAddressesByCity(@RequestParam String city){
        boolean personServiceOptional = Optional.ofNullable(personService
                .emailAddressesByCity(city)).isPresent();
        if(personServiceOptional){
            logger.info("Success.The list of all emails in the provided city was created.");
            return JsonStream.serialize(personService.emailAddressesByCity(city));
        } else {
            logger.info("ERROR During HTTP GET request." +
                    "The list of all emails in the provided city was not created.");
            return String.format("ERROR During HTTP GET request." +
                    "The list of all emails in the provided city was not created.");
        }
    }
}
