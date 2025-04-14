package com.controller;

import com.jsoniter.output.JsonStream;
import com.model.FireStation;
import com.model.PersonsListServicedByStation;
import com.service.FireStationService;
import com.service.JSONReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    final ExecutorService executorService = Executors.newFixedThreadPool(5);

    List<FireStation> fireStationList = new ArrayList<>();

    private void addDeletedFireStation() {
        try {
            Thread.sleep(15000);
            fireStationService.addNewFireStation(fireStationList.get(0));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @PostMapping("/firestation")
    public String addFireStation(@RequestBody FireStation fireStation) {
        logger.info("HTTP POST request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(fireStation.getAddress()));
        if (station.isEmpty()) {
            logger.info("Success. The firestation is created");
            return JsonStream.serialize(fireStationService.addNewFireStation(fireStation));
        } else {
            logger.error("ERROR During HTTP POST request. The firestation alredy exist");
            return String.format("ERROR During HTTP POST request. The firestation alredy exist");
        }
    }

    @GetMapping("/firestations")
    public String findAllFireStations() throws IOException {
        logger.debug("HTTP GET request received for /firestations URL");
        Optional<Boolean> station = Optional.ofNullable(fireStationService.findAllFireStations().isEmpty());
        if (station.get()) {
            logger.error("ERROR During HTTP GET request.The list of fire stations has been not created");
            return String.format("ERROR During HTTP GET request.The list of fire stations has been not created");
        } else {
            logger.info("Success. The list of firestations is created");
            return JsonStream.serialize(fireStationService.findAllFireStations());
        }
    }

    @PutMapping("/firestation/{address}")
    public void updateFireStation(@RequestBody FireStation fireStation, @PathVariable String address) {
        logger.debug("HTTP PUT request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(address));
        if (station.isPresent()) {
            logger.info("Success. The firestation is updated");
            fireStationService.updateFireStation(fireStation,address);
        } else {
            logger.error("ERROR During HTTP PUT request.The firestation not updated");
        }
    }

    @DeleteMapping("/firestation")
    public String deleteFireStation(@RequestParam String address) {
        logger.debug("HTTP DELETE request recieved at /firestation URL");
        Optional<FireStation> station = Optional.ofNullable(fireStationService
                .findFireStationByAddress(address));
        System.out.println(station);
        if (station.isPresent()) {
            fireStationList.add(station.get());
            logger.info("Success. The fire station is deleted");
            fireStationService.deleteFireStation(address);
            CompletableFuture.runAsync(()->{
                addDeletedFireStation();
            },executorService);
            return "The fire station was deleted successfully for demo purposes and will be restored in 15 seconds.";
        } else {
            logger.error("ERROR During HTTP DELETE request.The fire station not deleted");
            return "ERROR During HTTP DELETE request.The fire station not deleted";
        }
    }

    @GetMapping("/firestation")
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
        System.out.println(phoneNumberslist);
        if (phoneNumberslist.get()) {
            logger.error("ERROR During HTTP GET request. The list of phone numbers not created");
            return String.format("ERROR During HTTP GET request.The list of phone numbers not created");

        } else {
            logger.info("Success.The list of phone numbers was created.");
            return JsonStream.serialize(fireStationService
                    .phoneNumbersEachPersonWithinFireStationsJurisdiction(stationNumber));
        }
    }

    @GetMapping("/fire")
    public String findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt
    (@RequestParam("address") String address) {
        logger.debug("HTTP GET request recieved at /fire URL");
        Optional<Boolean> householdsAndListOfPeople = Optional.ofNullable(fireStationService
                .findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(address).isEmpty());
        System.out.println(householdsAndListOfPeople);
        if (householdsAndListOfPeople.get()) {
            logger.error("ERROR During HTTP GET request. Lists of addresses and people not created");
            return String.format("ERROR During HTTP GET request. Lists of addresses and people not created");
        } else {
            logger.info("Success. Lists of addresses and people were created.");
            return JsonStream.serialize(fireStationService
                    .findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(address));
        }
    }

    @GetMapping("/flood/stations")
    public String findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction
            (@RequestParam List<String> stations) {
        logger.debug("HTTP GET request recieved at /fire URL");
        Optional<Boolean> households = Optional.ofNullable(fireStationService
                .findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(stations).isEmpty());
        if (households.get()) {
            logger.error("ERROR During HTTP GET request. " +
                    "Lists of households and personal information of each habitant in it not created");
            return String.format("ERROR During HTTP GET request. " +
                    "Lists of households and personal information of each habitant in it not created");
        } else {
            logger.info("Success. Lists of households and personal information of each habitant in it were created.");
            return JsonStream.serialize(fireStationService
                    .findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(stations));
        }
    }

    @GetMapping("/checkEmail")
    public String checkEmail(@RequestParam(value = "email", required = false) String email) {
        System.out.println("Method called");
        return email;
    }
}
