package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository {

    List<FireStation> fireStations = new ArrayList<>();

    public FireStation findStationByAddress(String address) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }
        return null;
    }

    public List<FireStation> findAllStations() {
        return fireStations;
    }

    public FireStation addNewFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
        return fireStation;
    }

    public void updateFireStation(FireStation fireStation) {
        FireStation updateFireStation = findStationByAddress(fireStation.getAddress());
        updateFireStation.setStation(fireStation.getStation());
    }

    public void deleteFireStation(String address) {
        FireStation deleteFireStation = findStationByAddress(address);
        fireStations.remove(deleteFireStation);
    }

}
