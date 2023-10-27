package com.safetynet.safetynetalerts.controller;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.JSONReader;
import com.safetynet.safetynetalerts.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class MedicalRecordsController {

    private final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);

    private MedicalRecordsService medicalRecordsService;
    private JSONReader jsonReader;

    @Autowired
    public MedicalRecordsController(MedicalRecordsService medicalRecordsService, JSONReader jsonReader) {
        this.medicalRecordsService = medicalRecordsService;
        this.jsonReader = jsonReader;
    }

    @GetMapping("/medicalRecords")
    public String findAllMedicalRecords() {
        logger.info("HTTP GET request received for /medicalRecords URL");
        Optional<Boolean> medicalRecords = Optional.ofNullable(medicalRecordsService.findAllMedicalRecords().isEmpty());
        if (medicalRecords.get()) {
            logger.error("ERROR During HTTP GET request.The list of medical records has been not created");
            return String.format("ERROR During HTTP GET request.The list of medical records has been not created");
        } else {
            logger.info("Success. The list of medical records is received");
            return JsonStream.serialize(medicalRecordsService.findAllMedicalRecords());
        }
    }

    @PostMapping("/medicalRecord")
    public String addNewMedicalRecord(@RequestBody MedicalRecord record) {
        logger.info("HTTP POST request recieved at /medicalRecord URL");
        Optional<MedicalRecord> medicalRecord = Optional.ofNullable(medicalRecordsService
                .findMedicalRecordsByFirstAndLastName(record.getFirstName(),record.getLastName()));
        if (!medicalRecord.isPresent()) {
            logger.info("Success. The medical record is created");
            return JsonStream.serialize(medicalRecordsService.addNewMedicalRecord(record));
        } else {
           logger.error("ERROR During HTTP POST request. The medical record alredy exist");
           return String.format("ERROR During HTTP POST. The medical record alredy exist");
        }
    }

    @GetMapping("/medicalRecord")
    public String findMedicalRecordsByFirstAndLastName (@RequestParam String firstName,
                                                        @RequestParam String lastName){
        logger.info("HTTP GET request recieved at /medicalRecord URL");
        Optional<MedicalRecord> medicalRecord = Optional.ofNullable(medicalRecordsService
                .findMedicalRecordsByFirstAndLastName(firstName, lastName));
        if (medicalRecord.isPresent()) {
            logger.info("Success. The medical record is founded");
            return JsonStream.serialize(medicalRecordsService
                .findMedicalRecordsByFirstAndLastName(firstName,lastName));
       } else {
           logger.error("ERROR During HTTP GET request. The medical record record alredy exist");
           return String.format("ERROR During HTTP POST request. The medical record record exist");
        }
    }

    @PutMapping("/medicalRecord")
    public void updateMedicalRercord (@RequestBody MedicalRecord record){
        logger.info("HTTP PUT request recieved at /medicalRecord URL");
        Optional<MedicalRecord> medicalRecord = Optional.ofNullable(medicalRecordsService
                .findMedicalRecordsByFirstAndLastName(record.getFirstName(), record.getLastName()));
        if (medicalRecord.isPresent()) {
            logger.info("Success. The medical record is updated");
            medicalRecordsService.updateMedicalRercord(record);
        } else {
            logger.error("ERROR During HTTP PUT request.The medical record not updated");
        }
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@NotNull @RequestParam String firstName,
                                    @NotNull @RequestParam String lastName) {
        logger.info("HTTP DELETE request recieved at /medicalRecord URL");
        Optional<MedicalRecord> medicalRecord = Optional.ofNullable(medicalRecordsService
                .findMedicalRecordsByFirstAndLastName(firstName, lastName));
        if (medicalRecord.isPresent()) {
            logger.info("Success. The medical record deleted");
            medicalRecordsService.deleteMedicalRecord(firstName, lastName);
        } else {
            logger.error("ERROR During HTTP DELETE request.The medical record not deleted");
        }
    }
}
