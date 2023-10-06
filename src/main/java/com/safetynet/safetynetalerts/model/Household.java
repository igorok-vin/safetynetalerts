package com.safetynet.safetynetalerts.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return address.equals(household.address) && personList.equals(household.personList);}

    @Override
    public int hashCode() {
        return Objects.hash(address, personList);
    }
}
