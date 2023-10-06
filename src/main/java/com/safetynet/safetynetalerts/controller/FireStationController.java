package com.safetynet.safetynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.PersonsListServicedByStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.JSONReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FireStationController {

    private final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    private FireStationService fireStationService;
    private JSONReader jsonReader;

    @Autowired
    public FireStationController(FireStationService fireStationService, JSONReader jsonReader) {
        this.fireStationService = fireStationService;
        this.jsonReader = jsonReader;
    }

    @PostMapping("/firestation")
    public String addFireStation(@RequestBody FireStation fireStation) {
        logger.debug("HTTP POST request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(fireStation.getAddress()));
        if (!station.isPresent()) {
            logger.info("Success. The firestation is created");
            return JsonStream.serialize(fireStationService.addNewFireStation(fireStation));
        } else {
            logger.error("ERROR During HTTP POST request. The firestation alredy exist");
            return String.format("ERROR During HTTP POST request. The firestation alredy exist");
        }
    }

    @GetMapping("/firestations")
    public String findAllFireStations() {
        logger.debug("HTTP GET request received for /firestations URL");
        Optional<JSONReader> jsonReader1 = Optional.empty();
        if (!jsonReader1.isPresent()) {
            logger.info("Success. The list of firestations is created");
            return JsonStream.serialize(fireStationService.findAllFireStations());
        } else {
            logger.error("ERROR During HTTP GET request.The list of fire stations has been not created");
            return String.format("ERROR During HTTP GET request.The list of fire stations has been not created");
        }
    }

    @PutMapping("/firestation")
    public void updateFireStation(@RequestBody FireStation fireStation) {
        logger.debug("HTTP PUT request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(fireStation.getAddress()));
        if (station.isPresent()) {
            logger.info("Success. The firestation is updated");
            fireStationService.updateFireStation(fireStation);
        } else {
            logger.error("ERROR During HTTP PUT request.The firestation not updated");
        }
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestParam String address) {
        logger.debug("HTTP DELETE request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(address));
        if (station.isPresent()) {
            logger.info("Success. The firestation deleted");
            fireStationService.deleteFireStation(address);
        } else {
            logger.error("ERROR During HTTP DELETE request.The firestation not deleted");
        }
    }

    @GetMapping("/firestation") //
    public String listAdultsAndchildrenServicedByCorrespondingFireStation
            (@RequestParam("stationNumber") String stationNumber) {
        logger.debug("HTTP GET request recieved at /firestation URL");
        PersonsListServicedByStation personsListServicedByStation = fireStationService
                .listAdultsAndchildrenServicedByCorrespondingFireStation(stationNumber);
        if (!personsListServicedByStation.getPersonList().isEmpty()) {
            logger.info("Success.The list of people served by the corresponding fire station was created.");
            return JsonStream.serialize(fireStationService
                    .listAdultsAndchildrenServicedByCorrespondingFireStation(stationNumber));
        } else {
            logger.error("ERROR During HTTP GET request." +
                    "The list of people served by the corresponding fire station not created");
            return String.format("ERROR During HTTP GET request." +
                    "The list of people served by the corresponding fire station not created");
        }
    }

    @GetMapping("/phoneAlert")//
    public String phoneNumbersEachPersonWithinFireStationsJurisdiction
            (@RequestParam("firestation") String stationNumber) {
        logger.debug("HTTP GET request recieved at /phoneAlert URL");
        Optional<Boolean> phoneNumberslist = Optional.ofNullable(fireStationService
                .phoneNumbersEachPersonWithinFireStationsJurisdiction(stationNumber).isEmpty());
        if (phoneNumberslist.isPresent()) {
            logger.info("Success.The list of phone numbers was created.");
            return JsonStream.serialize(fireStationService
                    .phoneNumbersEachPersonWithinFireStationsJurisdiction(stationNumber));
        } else {
            logger.error("ERROR During HTTP GET request. The list of phone numbers not created");
            return String.format("ERROR During HTTP GET request.The list of phone numbers not created");
        }
    }

    @GetMapping("/fire")
    public String findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt
    (@RequestParam("address") String address) {
        logger.debug("HTTP GET request recieved at /fire URL");
        Optional<Boolean> householdsAndListOfPeople = Optional.ofNullable(fireStationService
                .findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(address).isEmpty());
        if (householdsAndListOfPeople.isPresent()) {
            logger.info("Success. Lists of addresses and people were created.");
            return JsonStream.serialize(fireStationService
                    .findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(address));
        } else {
            logger.error("ERROR During HTTP GET request. Lists of addresses and people not created");
            return String.format("ERROR During HTTP GET request. Lists of addresses and people not created");
        }
    }

    @GetMapping("/flood/stations")
    public String findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction
            (@RequestParam List<String> stations) {
        logger.debug("HTTP GET request recieved at /fire URL");
        Optional<Boolean> households = Optional.ofNullable(fireStationService
                .findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(stations).isEmpty());
        if (households.isPresent()) {
            logger.info("Success. Lists of households and personal information of each habitant in it were created.");
            return JsonStream.serialize(fireStationService
                    .findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(stations));
        } else {
            logger.error("ERROR During HTTP GET request. " +
                    "Lists of households and personal information of each habitant in it not created");
            return String.format("ERROR During HTTP GET request. " +
                    "Lists of households and personal information of each habitant in it not created");
        }
    }
}
