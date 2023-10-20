package com.safetynet.safetynetalerts.model;


import java.util.List;

public class PersonsListServicedByStation {
    private List<String> personList;
    private String numberOfAdults;
    private String numberOfChildren;
    private String stationNumber;

    public PersonsListServicedByStation(String station, String numberOfAdults,
                                        String numberOfChildren, List<String> personList) {
        this.stationNumber = station;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.personList = personList;
    }

    public List<String> getPersonList() { return personList; }
    public void setPersonList(List<String> personList) {
        this.personList = personList;
    }

    public String getNumberOfAdults() { return numberOfAdults; }
    public void setNumberOfAdults(String numberOfAdults) { this.numberOfAdults = numberOfAdults; }

    public String getNumberOfChildren() { return numberOfChildren; }
    public void setNumberOfChildren(String numberOfChildren) { this.numberOfChildren = numberOfChildren; }

}
