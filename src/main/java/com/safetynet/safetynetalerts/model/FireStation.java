package com.safetynet.safetynetalerts.model;

import java.util.Objects;

public class FireStation {

    private String address;
    private String station;

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getStation() { return station; }

    public void setStation(String station) { this.station = station; }

    @Override
    public String toString() {
        return "{"+"station ='" + station + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStation that = (FireStation) o;
        return address.equals(that.address) &&
                station.equals(that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
