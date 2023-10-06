package com.safetynet.safetynetalerts.model;


import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        PersonsListServicedByStation person = (PersonsListServicedByStation) obj;
        return  numberOfAdults.equals(person.numberOfAdults) && numberOfChildren.equals(person.numberOfChildren)
                && personList.equals(person.personList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfAdults, numberOfChildren, personList);
    }
}
