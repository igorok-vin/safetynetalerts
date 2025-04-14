package com.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.TypeLiteral;
import com.model.FireStation;
import com.model.MedicalRecord;
import com.model.Person;
import com.repository.FireStationRepository;
import com.repository.MedicalRecordRepository;
import com.repository.PersonRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JSONReader {

    private final Logger logger = LoggerFactory.getLogger(JSONReader.class);

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void readJsonFile() throws IOException {
        JSONParser jsonParser;
        try {
            //Reading works only to run from IDE not from JAR
            /*String jsonPath = "src/main/resources/data.json";
            byte[] allBytes = Files.readAllBytes(new File(jsonPath).toPath());
            JsonIterator jsonIterator = JsonIterator.parse(allBytes);*/

            //works everywhere in IDE and JAR
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream jsonPath = classLoader.getResourceAsStream("data.json");
            byte[] allBytes = jsonPath.readAllBytes();
            JsonIterator jsonIterator = JsonIterator.parse(allBytes);

            Any any = jsonIterator.readAny();//container that holds any values*/

           /*-------------------------------------------*/
            Any anyPerson = any.get("persons");
            anyPerson.forEach(person -> personRepository.addNewPerson(
                    new Person(person.get("firstName").toString(),
                                person.get("lastName").toString(),
                                person.get("address").toString(),
                                person.get("city").toString(),
                                person.get("zip").toString(),
                                person.get("phone").toString(),
                                person.get("email").toString())));
            logger.debug("Persons info have been loaded from the data.json file");

            /*-------------------------------------------*/
            Any anyFireStation = any.get("firestations");
            anyFireStation.forEach(fireStation -> fireStationRepository.addNewFireStation(
                    new FireStation(fireStation.get("address").toString(),
                                   (fireStation.get("station").toString()))));
            logger.debug("Fire stations info have been loaded from the data.json file");

            /*-------------------------------------------*/
            Any anyMedicalRecoreds = any.get("medicalrecords");
            anyMedicalRecoreds.forEach(medicalRecords -> medicalRecordRepository.addNewMedicalRecord(
                    new MedicalRecord(medicalRecords.get("firstName").toString(),
                                      medicalRecords.get("lastName").toString(),
                                      medicalRecords.get("birthdate").toString(),
                                      medicalRecords.get("medications").as(new TypeLiteral<List<String>>(){}),
                                      medicalRecords.get("allergies").as(new TypeLiteral<List<String>>(){}))));
            logger.debug("Medical records info have been loaded from the data.json file");

        } catch (IOException e) {
            logger.error("JSON file was not loaded");
        }
    }
}
