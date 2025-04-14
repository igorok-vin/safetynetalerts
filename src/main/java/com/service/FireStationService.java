package com.service;

import com.model.FireStation;
import com.model.Household;
import com.model.Person;
import com.model.PersonsListServicedByStation;

import java.util.List;
import java.util.Map;

public interface FireStationService {

    FireStation findFireStationByAddress(String address);

    List<FireStation> findAllFireStations();

    FireStation addNewFireStation(FireStation fireStation);

    void updateFireStation(FireStation fireStation,String address);

    void deleteFireStation(String address);

    List<String> findAllAddressesServicedByFireStation(String stationNumber);

    List<Person> findAllPersonsServicedByFireStation(String stationNumber);

    List<String> phoneNumbersEachPersonWithinFireStationsJurisdiction(String stationNumber);

    PersonsListServicedByStation listAdultsAndchildrenServicedByCorrespondingFireStation(String stationNumber);

    List<Household> findAllHouseholdsWithListOfPeopleInEachFireStationJurisdiction(List<String> stationsList);

    Map<String, List<Person>> findFireStationNumberThatServicesHouseholdAddressAndListOfPeopleInIt(String address);
}
