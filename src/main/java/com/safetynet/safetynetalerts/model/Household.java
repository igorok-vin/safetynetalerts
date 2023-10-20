package com.safetynet.safetynetalerts.model;

import java.util.List;

public class Household {
    private String address;
    private List<Person> personList;

    public Household(String address, List<Person> personList) {
        this.address = address;
        this.personList = personList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "Household{" +
                "address='" + address + '\'' +
                ", personList=" + personList +
                '}';
    }
}
